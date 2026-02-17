package org.example;

import java.util.List;

public class NotificationService {
    private final List<Notifier> channels;

    public NotificationService(List<Notifier> channels) {
        this.channels = channels;
    }

    public void notifyAll(String message) {
        // Send via all channels
    }

    public void notify(String channelType, String message) {
        // Find and use some channel
    }
}
