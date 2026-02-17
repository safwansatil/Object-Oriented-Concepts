package org.example;

import java.util.List;

public class OrderProcessor {
    private OrderValidator validator;
    private List<Payment> payments;
    private DatabaseHandler databaseHandler;
    private NotificationService notificationService;

    public OrderProcessor(OrderValidator validator, 
                         List<Payment> payments,
                         DatabaseHandler databaseHandler, 
                         NotificationService notificationService) {
        this.validator = validator;
        this.payments = payments;
        this.databaseHandler = databaseHandler;
        this.notificationService = notificationService;
    }

    public void process(Order order) throws OrderProcessingException {
        // 1. Validate
        // 2. display total
        // 3. Select and execute payment strategy
        // 4. Update database
        // 5. Send notifications
    }

    private Payment selectPaymentStrategy(PaymentType type) {
        // Find matching strategy from list
        return null;
    }
}
