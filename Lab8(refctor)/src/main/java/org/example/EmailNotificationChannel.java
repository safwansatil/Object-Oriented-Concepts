package org.example;

public class EmailNotificationChannel implements Notifier {
    
    @Override
    public void send(String message) {
        // Connect to email service
        // Send email
    }

    @Override
    public boolean supports(String channelType) {
        return "email".equalsIgnoreCase(channelType);
    }
}
