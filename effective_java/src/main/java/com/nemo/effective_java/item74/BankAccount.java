package com.nemo.effective_java.item74;

public class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	/**
	 * Deducts the amount from the balance.
	 *
	 * @param amount Amount to withdraw from the account
	 * @throws InsufficientBalanceException Occurs when the amount to withdraw exceeds the account balance
	 */
	public void withdraw(int amount) throws InsufficientBalanceException {
		if (balance < amount) {
			throw new InsufficientBalanceException("Insufficient balance amount:" + amount);
		}
		balance -= amount;
	}

	/**
	 * Deposits the amount into the balance.
	 *
	 * @param amount Amount to deposit into the account
	 * @throws IllegalArgumentException Occurs when the amount to deposit is negative
	 */
	public void deposit(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative amount:" + amount);
		}
		this.balance += amount;
	}
}
