package org.example;

class TransactionTask implements Runnable {
    private BankAccount account;
    public TransactionTask(BankAccount account) {
        this.account = account;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            account.deposit(1.0);
        }
    }
}