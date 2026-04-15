package com.codurance.ocp;

public class Engineer extends Employee implements PayEmployee {
    public Engineer(int salary, int bonus) {
        super(salary, bonus);
        this.type = EmployeeType.ENGINEER;
    }
    @Override
    public int payAmount(){
        return this.getSalary();
    }
    
}
