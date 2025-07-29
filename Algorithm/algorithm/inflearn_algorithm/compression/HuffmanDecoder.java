package compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class HuffmanDecoder {
	public static void decoder(String fileName) throws IOException
	{
		HuffmanCoding app = new HuffmanCoding();
		RandomAccessFile fIn = null;
		
		try {
			fIn = new RandomAccessFile(fileName, "r");
			app.decompressFile(fileName, fIn);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			fIn.close();
		}
		
		System.out.println("decode 파일 내용");
		fIn = new RandomAccessFile(fileName+".dec", "r");
		while(fIn.getFilePointer() < fIn.length())
		{
			System.out.print((char) fIn.read());
		}
	}
}
