package org.example;

public class WpNotifier implements Notifier {
    
    @Override
    public void send(String message) {
        // send to wp logic
    }

    @Override
    public boolean supports(String channelType) {
        return "whatsapp".equalsIgnoreCase(channelType);
    }
}
