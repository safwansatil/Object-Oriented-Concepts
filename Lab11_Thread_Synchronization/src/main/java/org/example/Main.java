package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount acc1 = new BankAccount();
        BankAccount acc2 = new BankAccount();
// TODO: Create 4 threads

// Thread 1 & 2 target acc1
// Thread 3 & 4 target acc2
        Thread t1 = new Thread(new TransactionTask(acc1));
        Thread t2 = new Thread(new TransactionTask(acc1));
        Thread t3 = new Thread(new TransactionTask(acc2));
        Thread t4 = new Thread(new TransactionTask(acc2));
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();
        System.out.println("Account 1 Balance (Expected 2000): " + acc1.
                getBalance());
        System.out.println("Account 2 Balance (Expected 2000): " + acc2.
                getBalance());
        System.out.println("Total Bank Transactions (Expected 4000): " +
                BankAccount.getTotalBankTransactions());
    }
}