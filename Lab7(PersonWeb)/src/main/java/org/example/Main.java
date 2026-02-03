package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Person Management System...");
        try {
            App.main(args);
        } catch (IOException e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
