package com.nemo.effective_java.item71.step01;

public class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public void withdraw(int amount) throws BankAccountException {
		if (amount > balance) {
			throw new BankAccountException(amount);
		}
		balance -= amount;
	}

	public static void main(String[] args) {
		BankAccount bankAccount = new BankAccount(10000);
		int amount = 20000;
		try {
			bankAccount.withdraw(amount);
		} catch (BankAccountException e) {
			System.out.println(e.getMessage());
		}
	}
}
