package org.example.Calculator;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(
        targetNamespace = "http://calculator.example.com/"
)
public interface Calculator {

    @WebMethod
    int add(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b
    );

    @WebMethod
    int subtract(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b
    );

    @WebMethod
    int multiply(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b
    );

    @WebMethod
    int divide(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b
    );
}
