package org.example;

public class Main {
    public static void main(String[] args) {

        String endpoint = "http://localhost:8080/calculator";

        // SOAP body for add(a=5, b=3)
        String soapBody =
                "<cal:add xmlns:cal=\"http://calculator.example.com/\">" +
                        "<a>5</a>" +
                        "<b>3</b>" +
                        "</cal:add>";

        // Wrap body in SOAP envelope
        String soapEnvelope = SimpleSoapClient.createSoapEnvelope(soapBody);

        // Send request
        String response = SimpleSoapClient.sendSoapRequest(
                endpoint,
                "", // SOAPAction (empty is correct for JAX-WS)
                soapEnvelope
        );

        // raw SOAP response
        System.out.println("SOAP Response:");
        System.out.println(response);
    }
}
