package com.nemo.effective_java.item28.step01;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Object[] arr = new Long[1];
		try{
			arr[0] = "I don't fit in"; // RuntimeError: ArrayStoreException
		}catch (ArrayStoreException e){
			System.out.println(e);
		}

		// List<Object> list = new ArrayList<Long>(); // Compile Error: Incompatible types: Long is not a subtype of Object

	}
}
