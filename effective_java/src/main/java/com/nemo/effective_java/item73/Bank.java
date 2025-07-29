package com.nemo.effective_java.item73;

public class Bank {
	private final BankAccount bankAccount;

	public Bank(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void processWithdrawal(int amount) throws BankTransactionException {
		try {
			bankAccount.withdraw(amount);
		} catch (InsufficientBalanceException e) {
			// exception chaining (warping the LowLevel exception to HighLevel exception)
			throw new BankTransactionException("Error prcessing withdrawal", e);
		}
	}
}
