package com.nemo.effective_java.item03.step01;

public class Elvis {
	public static final Elvis INSTANCE = new Elvis();

	private Elvis() {
	}

	public void leaveTheBuilding() {
		System.out.println("call leaveTheBuilding");
	}
}
