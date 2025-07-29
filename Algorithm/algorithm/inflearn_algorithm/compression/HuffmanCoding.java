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
	private Run theRoot;	// ������ Ʈ���� ��Ʈ
	private List<Run>[] chars;
	private long sizeOriginalFile;	// ���������� ����

	public HuffmanCoding() {
		runs = new ArrayList<Run>();
		chars = new List[256];	// �ƽ�Ű �ڵ� 0~255�� ���� �迭
		
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
		
		// 1. ������ ��� run ��ü���� ����
		runs.stream().forEach((run)->heap.add(run));
				
		// 2. heap�� ũ�Ⱑ 1�� �ɶ����� �ݺ��� ����
		while(heap.size() > 1)
		{
			// 2.1 heap���� run�� �ΰ� ����
			Run first = heap.remove();
			Run second = heap.remove();
			
			// 2.2 �ΰ��� run�� �ϳ��� ���� Ʈ���� ����
			Run tree = makeBinaryTree(first, second);

			// 2.3 ������ ���� Ʈ���� heap�� ����
			heap.add(tree);
		}
		
		// 3. theRoot�� heap�� ��Ʈ�� ����Ŵ
		theRoot = heap.getMin();
	}
	
	// first, seocnd run ��ü�� �ڽ����� �δ� ���� Ʈ���� ������
	private Run makeBinaryTree(Run first, Run second) {
		Run tree = new Run((byte) 0, 0, first.freq + second.freq);
		
		tree.left = first;
		tree.right = second;
		
		return tree;
	}
	
	private void assignCodewords(Run p, int codeword, int length) {
		if(p.left == null && p.right == null)
		{
			// �����ϰ��� �ϴ� ���ڰ� 1���� ���
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
			// ���� �ڽ� ��忡�Դ� codeword�� �ڿ� 0�� �߰�
			// ������ �ڽ� ��忡�Դ� codeword�� �ڿ� 1�� �߰�			
			assignCodewords(p.left,  codeword << 1, length + 1);
			assignCodewords(p.right, (codeword << 1) + 1, length + 1);
		}
	}
	
	private void storeRunsIntoArray(Run p) {
		if(p.left == null && p.right == null)
		{
			// �迭 chars[(unsigned int) p.symbol]�� ����Ű��
			// ���Ḯ��Ʈ�� �� �տ� p�� ����
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
	 * �迭 chars���� (symbol, length)�� �ش��ϴ� run�� ã�� ��ȯ
	 * @param symbol ������ ����Ʈ��
	 * @param length runLength
	 * @return run ��ü
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
	 * run ��ü�� �ִ� codeword�� ���
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
	 * codeword�� ���� ������ byte���� ���ۿ� ����
	 * ���� ������ ũ�Ⱑ 32byte�� �Ǹ� ������ ������ ��������(fOut)�� �ۼ��ϰ�
	 * ������ ��� 
	 * @param codeword �� ���ڿ� ���� codeword
	 * @param buffer codeword�� byte���� �����ϴ� ����
	 * @param fOut ��������
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
	 * ���� ������ ����� Run�鿡 ���� ������ ���������� ũ�⸦ ���
	 * @param fIn	: ������ ����
	 * @param fOut  : ����� ����
	 * @throws IOException 
	 */
	private void outputFrequencies(RandomAccessFile fIn
								 , RandomAccessFile fOut) throws IOException
	{
		fOut.writeInt(runs.size());	// run�� ���� ���, 4byte�� �����
		
		fOut.writeLong(fIn.getFilePointer());	// ���� ������ ũ��(byte����)�� ���, 8byte�� �����
		
		// ������ Run ��ü�� ���� ������ ���� ���Ͽ� ���
		for(int i = 0; i < runs.size(); i++)
		{
			Run r = runs.get(i);
			fOut.write(r.symbol);	// 1byte ����
			fOut.writeInt(r.runLen);// 4byte ����
			fOut.writeInt(r.freq);  // 4byte ����
		}
	}
	
	/**
	 * fIn ������ run ��ü ������ �о runs�� �߰���
	 * @param fIn
	 * @throws IOException
	 */
	private void inputFrequencies(RandomAccessFile fIn) throws IOException {
		int dataIndex = fIn.readInt();		// Run ��ü���� ����
		sizeOriginalFile = fIn.readLong();	// ���������� ũ��
		runs.ensureCapacity(dataIndex);		// ����Ʈ ũ�� ����
		
		for(int i = 0; i < dataIndex; i++)
		{
			Run r = new Run();
			r.symbol = (byte) fIn.read();	// ����
			r.runLen = fIn.readInt();		// ������ ����
			r.freq = fIn.readInt();			// �󵵼�
			runs.add(r);
		}
	}
	
	
	/**
	 * ���� ���� �޼���
	 * fIn ���� ������ �����Ͽ� fOut�� ���
	 * @param fIn  : ���� ����
	 * @param fOut : ���� ����
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
				// run �ν� & codeword Ž��
				Run run = findRun(prev_b, runLen);
				String codeword = getCodeword(run);
				// codeword�� buffer�� pack
				// ���� ���۰� ��á�ٸ� ��� ���Ͽ� ���ۿ� �ִ� ���� �ۼ�
				packCodewordToBuffer(codeword, buffer, fOut);
				runLen = 0;		
			}
			prev_b = b;
			runLen++;
		
			// ������ ������ ���
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
	 * ���������� ���������ϴ� �޼���
	 * @param fIn ��������
	 * @param fOut ���������� ����
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
		

		String outFileName = new String(inFileName+".z"); // ���� ���� �̸� ����
		RandomAccessFile fOut = new RandomAccessFile(outFileName, "rw");
		fOut.setLength(0);
		
		try {
			collectRuns(fIn);					// step1. Run ��ü ����
			outputFrequencies(fIn, fOut);		// step2. �������� ����� Run��ü ���� �� ��������ũ�� ����
			createHuffmanTree();				// step3. Huffman Tree ���� 
			assignCodewords(theRoot, 0, 0);		// step4. ������ Run ��ü�� Codeword �Ҵ�
			storeRunsIntoArray(theRoot);		// step4. ������ Run ��ü�� �迭�� ����
			fIn.seek(0);					// ������ �� �պκ����� �̵�
			encode(fIn, fOut);					// step5. fIn�� ������ ����(encode)
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(outFileName + " ����");
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
