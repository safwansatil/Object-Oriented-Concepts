package org.example;


public interface Notifier {
    void send(String message);
    boolean supports(String channelType);
}
