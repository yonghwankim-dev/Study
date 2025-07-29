package com.nemo.effective_java.item71.step01;

public class BankAccountException extends Exception {

	public BankAccountException(int amount) {
		super("Insufficient balance, amount : " + amount);
	}
}
