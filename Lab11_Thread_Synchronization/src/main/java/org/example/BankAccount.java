package org.example;


class BankAccount {
    private double balance = 0;
    private static int totalBankTransactions = 0;
    // TODO: Implement non-static synchronized deposit

    public synchronized void deposit(double amount) {
        this.balance += amount;
        incrementTotalTransactions();
    }
    // TODO: Implement static synchronized increment
    public static synchronized void incrementTotalTransactions() {
        totalBankTransactions++;
    }
    public double getBalance() { return balance; }
    public static int getTotalBankTransactions() { return totalBankTransactions;
    }
}
