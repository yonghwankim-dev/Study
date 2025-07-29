package com.nemo.effective_java.item74;

/**
 * The type Bank.
 * <p>
 * This Bank class throws a BankTransactionException when a bank transaction fails.
 * </p>
 */
public class Bank {
	private final BankAccount bankAccount;

	public Bank(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * Withdraws the specified amount from the bank account's balance.
	 *
	 * @param amount Amount to withdraw from the account
	 * @throws BankTransactionException Occurs when a bank transaction fails
	 */
	public void processWithdrawal(int amount) throws BankTransactionException {
		try {
			bankAccount.withdraw(amount);
		} catch (InsufficientBalanceException e) {
			throw new BankTransactionException("Error prcessing withdrawal", e);
		}
	}

	/**
	 * Deposits the specified amount into the bank account's balance.
	 *
	 * @param amount Amount to deposit into the account
	 * @throws BankTransactionException Occurs when a bank transaction fails
	 */
	public void processDeposit(int amount) throws BankTransactionException {
		try {
			bankAccount.deposit(amount);
		} catch (IllegalArgumentException e) {
			throw new BankTransactionException("Error processing deposit", e);
		}
	}
}
