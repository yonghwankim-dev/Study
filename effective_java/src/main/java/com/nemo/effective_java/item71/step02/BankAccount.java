package com.nemo.effective_java.item71.step02;

public class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public boolean hasSufficientBalance(int amount) {
		return amount <= balance;
	}

	public void withdraw(int amount) {
		balance -= amount;
	}

	public static void main(String[] args) {
		BankAccount bankAccount = new BankAccount(10000);

		int amount = 20000;

		if (bankAccount.hasSufficientBalance(amount)) {
			bankAccount.withdraw(amount);
		} else {
			throw new IllegalArgumentException("Insufficient balance, amount : " + amount);
		}
	}
}
