package org.example.Calculator;

import jakarta.xml.ws.Endpoint;

public class CalculatorPublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://localhost:8080/calculator",
                new CalculatorService()
        );
        System.out.println("SOAP Web Service is running...");
    }
}
