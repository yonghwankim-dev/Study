package com.nemo.effective_java.item73;

public class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public void withdraw(int amount) throws InsufficientBalanceException {
		if (balance < amount) {
			throw new InsufficientBalanceException("Insufficient balance amount:" + amount);
		}
		balance -= amount;
	}
}
