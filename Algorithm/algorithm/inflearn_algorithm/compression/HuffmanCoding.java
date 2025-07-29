package compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringBufferInputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class HuffmanCoding {
	private ArrayList<Run> runs;
	private Heap<Run> heap;		// min heap
	private Run theRoot;	// 허프만 트리의 루트
	private List<Run>[] chars;
	private long sizeOriginalFile;	// 원본파일의 길이

	public HuffmanCoding() {
		runs = new ArrayList<Run>();
		chars = new List[256];	// 아스키 코드 0~255에 대한 배열
		
		for(int i=0; i<chars.length; i++)
		{
			chars[i] = new LinkedList<Run>();
		}
	}
	
	private boolean checkSymbol(byte symbol, int runLen)
	{
		for(Run r : runs)
		{
			if(r.equals(symbol, runLen))
			{
				r.freq++;
				return true;
			}
		}
		return false;
	}
	
	void collectRuns(RandomAccessFile fIn) throws IOException
	{
		int ch=-1;
		List<Byte> list = new ArrayList<Byte>();
		int count = 0;
		byte prev_symbol = 0;
		
		while((ch = fIn.read())!=-1)
		{
			byte symbol = (byte) ch;
			
			if(list.isEmpty())
			{
				list.add(symbol);		
			}
			else if(prev_symbol==symbol)
			{
				list.add(symbol);
			}
			else
			{
				if(checkSymbol(prev_symbol, count))
				{
					list.clear();
					list.add(symbol);
					count = 0;
				}
				else
				{
					runs.add(new Run(prev_symbol, count, 1));
					list.clear();
					list.add(symbol);
					count = 0;
				}
				
			}
			prev_symbol = symbol;
			count++;		
		}
		
		if(!list.isEmpty())
		{
			if(!checkSymbol(prev_symbol, count))
			{
				runs.add(new Run(prev_symbol, count, 1));
			}
		}
	}

	void createHuffmanTree() {
		heap = new Heap<Run>(256);
		
		// 1. 힙으로 모든 run 객체들을 저장
		runs.stream().forEach((run)->heap.add(run));
				
		// 2. heap의 크기가 1이 될때까지 반복문 수행
		while(heap.size() > 1)
		{
			// 2.1 heap에서 run을 두개 추출
			Run first = heap.remove();
			Run second = heap.remove();
			
			// 2.2 두개의 run을 하나의 이진 트리로 생성
			Run tree = makeBinaryTree(first, second);

			// 2.3 생성한 이진 트리를 heap에 넣음
			heap.add(tree);
		}
		
		// 3. theRoot는 heap의 루트를 가리킴
		theRoot = heap.getMin();
	}
	
	// first, seocnd run 객체를 자식으로 두는 이진 트리를 생성함
	private Run makeBinaryTree(Run first, Run second) {
		Run tree = new Run((byte) 0, 0, first.freq + second.freq);
		
		tree.left = first;
		tree.right = second;
		
		return tree;
	}
	
	private void assignCodewords(Run p, int codeword, int length) {
		if(p.left == null && p.right == null)
		{
			// 압축하고자 하는 문자가 1개인 경우
			if(p == theRoot)
			{
				p.codeword = 0;
				p.codewordLen = 1;
			}
			else
			{
				p.codeword = codeword;
				p.codewordLen = length;
			}
		}
		else
		{
			// 왼쪽 자식 노드에게는 codeword의 뒤에 0을 추가
			// 오른쪽 자식 노드에게는 codeword의 뒤에 1을 추가			
			assignCodewords(p.left,  codeword << 1, length + 1);
			assignCodewords(p.right, (codeword << 1) + 1, length + 1);
		}
	}
	
	private void storeRunsIntoArray(Run p) {
		if(p.left == null && p.right == null)
		{
			// 배열 chars[(unsigned int) p.symbol]가 가리키는
			// 연결리스트의 맨 앞에 p를 삽입
			insertToArray(p);
		}
		else
		{
			storeRunsIntoArray(p.left);
			storeRunsIntoArray(p.right);
		}
	}
	
	private void insertToArray(Run p) {
		List<Run> run_list = chars[Integer.parseUnsignedInt(String.valueOf(p.symbol))];
		Run prev_run;
		
		if(!run_list.isEmpty())
		{
			prev_run = run_list.get(0);
			p.right = prev_run;
		}
		run_list.add(0, p);
	}
	

	/**
	 * 배열 chars에서 (symbol, length)에 해당하는 run을 찾아 반환
	 * @param symbol 문자의 바이트형
	 * @param length runLength
	 * @return run 객체
	 */
	public Run findRun(byte symbol, int length)
	{
		List<Run> run_list = chars[Integer.parseUnsignedInt(String.valueOf(symbol))];
		
		for(Run run : run_list)
		{
			if(run.runLen == length)
			{
				return run;
			}
		}
		return null;
	}
	
	/**
	 * run 객체에 있는 codeword를 계산
	 * @param run
	 * @return codeword
	 */
	private String getCodeword(Run run)
	{
		int codeword = run.codeword;
		int codewordLen = run.codewordLen;
		String result = "";
		
		for(int i = 0; i < codewordLen; i++)
		{
			result = (codeword%2) + result;
			codeword /= 2;
		}
		return result;
	}
	
	/**
	 * codeword에 대한 각각의 byte들을 버퍼에 저장
	 * 만약 버퍼의 크기가 32byte가 되면 버퍼의 내용을 압축파일(fOut)에 작성하고
	 * 내용을 비움 
	 * @param codeword 각 문자에 대한 codeword
	 * @param buffer codeword의 byte들을 저장하는 버퍼
	 * @param fOut 압축파일
	 * @throws IOException
	 */
	private void packCodewordToBuffer(String codeword, StringBuffer buffer, RandomAccessFile fOut) throws IOException
	{		
		for(int i = 0; i < codeword.length(); i++)
		{
			if(buffer.length()==32)
			{
				fOut.writeBytes(buffer.toString());
				buffer.setLength(0);
			}
			buffer.append(codeword.charAt(i));
		}
	}
	
	/**
	 * 압축 파일의 헤더에 Run들에 대한 정보와 원본파일의 크기를 출력
	 * @param fIn	: 압축할 파일
	 * @param fOut  : 압축된 파일
	 * @throws IOException 
	 */
	private void outputFrequencies(RandomAccessFile fIn
								 , RandomAccessFile fOut) throws IOException
	{
		fOut.writeInt(runs.size());	// run의 개수 출력, 4byte로 저장됨
		
		fOut.writeLong(fIn.getFilePointer());	// 원본 파일의 크기(byte단위)를 출력, 8byte로 저장됨
		
		// 각각의 Run 객체에 대한 정보를 압축 파일에 출력
		for(int i = 0; i < runs.size(); i++)
		{
			Run r = runs.get(i);
			fOut.write(r.symbol);	// 1byte 저장
			fOut.writeInt(r.runLen);// 4byte 저장
			fOut.writeInt(r.freq);  // 4byte 저장
		}
	}
	
	/**
	 * fIn 파일의 run 객체 정보를 읽어서 runs에 추가함
	 * @param fIn
	 * @throws IOException
	 */
	private void inputFrequencies(RandomAccessFile fIn) throws IOException {
		int dataIndex = fIn.readInt();		// Run 객체들의 개수
		sizeOriginalFile = fIn.readLong();	// 원본파일의 크기
		runs.ensureCapacity(dataIndex);		// 리스트 크기 설정
		
		for(int i = 0; i < dataIndex; i++)
		{
			Run r = new Run();
			r.symbol = (byte) fIn.read();	// 문자
			r.runLen = fIn.readInt();		// 문자의 길이
			r.freq = fIn.readInt();			// 빈도수
			runs.add(r);
		}
	}
	
	
	/**
	 * 파일 압축 메서드
	 * fIn 파일 내용을 입축하여 fOut에 출력
	 * @param fIn  : 원본 파일
	 * @param fOut : 압축 파일
	 * @throws IOException 
	 */
	private void encode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException
	{
		byte prev_b = 0;
		int runLen = 0;
		StringBuffer buffer = new StringBuffer(32);
		
		while(fIn.getFilePointer() < fIn.length())
		{
			byte b = fIn.readByte();
			
			if(prev_b != 0 && b != prev_b)
			{
				// run 인식 & codeword 탐색
				Run run = findRun(prev_b, runLen);
				String codeword = getCodeword(run);
				// codeword를 buffer로 pack
				// 만약 버퍼가 꽉찼다면 출력 파일에 버퍼에 있는 것을 작성
				packCodewordToBuffer(codeword, buffer, fOut);
				runLen = 0;		
			}
			prev_b = b;
			runLen++;
		
			// 마지막 문자인 경우
			if(fIn.getFilePointer() >= fIn.length())
			{
				Run run = findRun(prev_b, runLen);
				String codeword = getCodeword(run);
				packCodewordToBuffer(codeword, buffer, fOut);
			}
		}
		fOut.writeBytes(buffer.toString());
	}
	
	/**
	 * 압축파일을 압축해제하는 메서드
	 * @param fIn 압축파일
	 * @param fOut 압축해제한 파일
	 * @throws IOException 
	 */
	private void decode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException {
		int ch;
		Run p = null;
		
		while(fIn.getFilePointer() < fIn.length())
		{
			ch = fIn.read()-48;
			p = theRoot;
			while(true)
			{
				if(p.left == null && p.right == null)
				{
					for(int i = 0; i < p.runLen; i++)
					{
						fOut.write(p.symbol);
					}
					break;
				}
				else if(ch == 0)
				{
					p = p.left;
				}
				else
				{
					p = p.right;
				}
				
				if(p.left != null || p.right != null)
				{
					ch = fIn.read()-48;
				}
			}
		}
	}
	
	public void compressFile(String inFileName, RandomAccessFile fIn) throws IOException{
		

		String outFileName = new String(inFileName+".z"); // 압축 파일 이름 설정
		RandomAccessFile fOut = new RandomAccessFile(outFileName, "rw");
		fOut.setLength(0);
		
		try {
			collectRuns(fIn);					// step1. Run 객체 수집
			outputFrequencies(fIn, fOut);		// step2. 압축파일 헤더에 Run객체 정보 및 원본파일크기 저장
			createHuffmanTree();				// step3. Huffman Tree 생성 
			assignCodewords(theRoot, 0, 0);		// step4. 각각의 Run 객체에 Codeword 할당
			storeRunsIntoArray(theRoot);		// step4. 각각의 Run 객체들 배열에 저장
			fIn.seek(0);					// 파일의 맨 앞부분으로 이동
			encode(fIn, fOut);					// step5. fIn의 내용을 압축(encode)
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(outFileName + " 내용");
		fOut.seek(0);
		while(fOut.getFilePointer() < fOut.length())
		{
			System.out.print(fOut.read() + " ");
		}
		System.out.println();
	}
	
	public void decompressFile(String inFileName, RandomAccessFile fIn) throws IOException {
		String outFileName = new String(inFileName + ".dec");
		RandomAccessFile fOut = new RandomAccessFile(outFileName, "rw");
		fOut.setLength(0);
		
		inputFrequencies(fIn);
		createHuffmanTree();
		assignCodewords(theRoot, 0, 0);
		decode(fIn, fOut);
	}
	
	void printHuffmanTree() {
		preOrderTraverse(theRoot, 0);
	}
	
	private void preOrderTraverse(Run node, int depth) {
		for(int i=0; i<depth; i++)
		{
			System.out.print(" ");
		}
		
		if(node == null)
		{
			System.out.println("null");
		}
		else
		{
			System.out.println(node.toString());
			preOrderTraverse(node.left, depth + 1);
			preOrderTraverse(node.right, depth + 1);
		}
	}
	
	public ArrayList<Run> getRuns() {
		return runs;
	}

	public List<Run>[] getChars() {
		return chars;
	}


	
	
}
