package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleSoapClient {

    public static String sendSoapRequest(String soapEndpoint, String soapAction, String soapEnvelope) {
        try {
            // Create URL connection
            URL url = URI.create(soapEndpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            connection.setRequestProperty("SOAPAction", soapAction);
            connection.setDoOutput(true);

            // Send SOAP envelope
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = soapEnvelope.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            // Read response
            StringBuilder response = new StringBuilder();
            int responseCode = connection.getResponseCode();

            InputStream inputStream = (responseCode >= 200 && responseCode < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }
            }

            connection.disconnect();
            return response.toString();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Helper
    public static String createSoapEnvelope(String soapBody) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Header/>\n" +
                "  <soap:Body>\n" +
                soapBody + "\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
    }
}