package com.nemo.effective_java.item19.step03;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	public static void main(String[] args) {
		try {
			// Serialize
			SubClass obj = new SubClass("Test", 42);
			FileOutputStream fileOut = new FileOutputStream("subclass.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();

			// Deserialize
			FileInputStream fileIn = new FileInputStream("subclass.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			SubClass deserializedObj = (SubClass) in.readObject();
			in.close();
			fileIn.close();

			System.out.println("Deserialized Object: " + deserializedObj);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
