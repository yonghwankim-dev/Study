package com.nemo.effective_java.item73;

public class BankClient {
	public static void main(String[] args) {
		BankAccount bankAccount = new BankAccount(10000);
		Bank bank = new Bank(bankAccount);

		int amount = 20000;
		try {
			bank.processWithdrawal(amount);
		} catch (BankTransactionException e) {
			// process HighLevel Exception
			e.printStackTrace(System.out);
		}
	}
}
