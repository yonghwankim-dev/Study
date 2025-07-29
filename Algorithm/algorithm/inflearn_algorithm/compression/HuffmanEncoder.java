package compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class HuffmanEncoder {
	public static void encoder(String fileName) throws IOException
	{
		HuffmanCoding app = new HuffmanCoding();
		RandomAccessFile fIn = null;
		
		try {
			fIn = new RandomAccessFile(fileName, "r");
			app.compressFile(fileName, fIn);
			System.out.println("encode ¿Ï·á");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			fIn.close();
		}	
	}
}
