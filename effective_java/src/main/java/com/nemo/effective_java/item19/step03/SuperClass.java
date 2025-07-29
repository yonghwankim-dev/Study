package com.nemo.effective_java.item19.step03;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class SuperClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;

	public SuperClass(String name) {
		this.name = name;
	}

	// implement readObject
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// process deserialize
		in.defaultReadObject();

		// calling method of overridable
		afterRead();
	}

	// 재정의 가능한 메서드
	protected void afterRead() {
		System.out.println("SuperClass: afterRead() called");
	}

	@Override
	public String toString() {
		return "SuperClass{name='" + name + "'}";
	}
}
