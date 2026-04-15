package com.codurance.ocp;

public class Employee {

    private int salary;
    private int bonus;
    EmployeeType type;

    Employee(int salary, int bonus) {
        this.salary = salary;
        this.bonus = bonus; 
    }  
    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

}