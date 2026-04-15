package com.codurance.ocp;

public class Manager extends Employee implements PayEmployee {

    public Manager(int salary, int bonus) {
        super(salary, bonus);
        this.type = EmployeeType.MANAGER;
    }
    @Override
    public int payAmount(){
        return this.getSalary() + this.getBonus();
    }
}
