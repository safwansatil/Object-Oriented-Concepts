package com.codurance.srp;

import java.util.List;

public class AccountService {
    private printer p;
    private TransactionRepository transactionRepository;
    private Clock clock;
    private Console console;

    public AccountService(TransactionRepository transactionRepository, Clock clock, Console console, printer p) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
        this.console = console;
        this.p = p;
    }

    public void deposit(int amount) {
        transactionRepository.add(transactionWith(amount));
    }
    public void withdraw(int amount) {
        transactionRepository.add(transactionWith(-amount));
    }
    private Transaction transactionWith(int amount) {
        return new Transaction(clock.today(), amount);
    }
    public void printStatement(){
        List<Transaction> toPrint = transactionRepository.all();
        this.p.printStatement(toPrint);
    }    
}