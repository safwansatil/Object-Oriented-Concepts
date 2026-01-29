package org.example.Calculator;


import jakarta.jws.WebService;

@WebService(
        endpointInterface = "org.example.Calculator.Calculator",
        targetNamespace = "http://calculator.example.com/"
)
public class CalculatorService implements Calculator {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int subtract(int a, int b) {
        return a-b;
    }

    @Override
    public int multiply(int a, int b) {
        return a*b;
    }

    @Override
    public int divide(int a, int b) {
        if(b==0){
            throw new Error("You cant divide by zero");
        }
        return a/b;
    }
}
