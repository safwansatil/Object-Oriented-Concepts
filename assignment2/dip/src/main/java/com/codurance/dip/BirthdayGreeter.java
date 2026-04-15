package com.codurance.dip;
import java.time.MonthDay;

public class BirthdayGreeter {
    private final EmployeeRepository employeeRepository;
    private final Clock clock;
    private final Sender sender;

    public BirthdayGreeter(EmployeeRepository employeeRepository, Clock clock, Sender sender) {
        this.employeeRepository = employeeRepository;
        this.clock = clock;
        this.sender = sender;
    }

    public void sendGreetings() {
        MonthDay today = clock.monthDay();
        employeeRepository.findEmployeesBornOn(today)
                .stream()
                .map(this::emailFor)
                .forEach(sender::send);
    }

    private Email emailFor(Employee employee) {
        String message = String.format("Happy birthday, dear %s!", employee.getFirstName());
        return new Email(employee.getEmail(), "Happy birthday!", message);
    }

}