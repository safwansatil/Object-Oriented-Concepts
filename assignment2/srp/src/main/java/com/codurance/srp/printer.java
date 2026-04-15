package com.codurance.srp;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toCollection;

public class printer {
    Console cn;
    formatter f;
    private static final String STATEMENT_HEADER = "DATE | AMOUNT | BALANCE";

    public printer(Console c, formatter f) {
        this.cn = c;
        this.f = f;
    }

    
    public void printStatement(List<Transaction> transactions) {
        printHeader();
        printTransactions(transactions);
    }


    private void printHeader() {
        printLine(STATEMENT_HEADER);
    }


    private void printTransactions(List<Transaction> transactions) {
        final AtomicInteger balance = new AtomicInteger(0);
        transactions.stream()
                .map(transaction -> statementLine(transaction, balance.addAndGet(transaction.amount()), f))
                .collect(toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(this::printLine);
    }
    private void printLine(String line) {
        cn.printLine(line);
    }
    private String statementLine(Transaction transaction, int balance, formatter f) {
        return MessageFormat.format("{0} | {1} | {2}", f.formatDate(transaction.date()), f.formatNumber(transaction.amount()), f.formatNumber(balance));
    }
}
