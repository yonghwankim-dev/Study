package com.nemo.effective_java.item09.step02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FruitFileCopyer {
	public static void copy(String src, String dst) throws IOException {
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dst);
			try {
				byte[] buf = new byte[1024];
				int n;
				while ((n = in.read(buf)) >= 0)
					out.write(buf, 0, n);
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

	public static void main(String[] args) throws IOException {
		String src = "src/main/resources/fruits.txt";
		String dst = "src/main/resources/fruits_copy.txt";
		copy(src, dst);
	}
}
