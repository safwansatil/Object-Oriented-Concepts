package com.codurance.ocp;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class EmployeeShould {

    private static final int BONUS = 100;
    private static final int SALARY = 1000;

    @Test
    public void not_add_bonus_to_the_engineer_pay_amount() {
        Engineer engineer = new Engineer(SALARY, 0);
        assertThat(engineer.payAmount())
                .isEqualTo(SALARY);
    }

    @Test
    public void add_bonus_to_the_manager_pay_amount() {
        Manager manager = new Manager(SALARY, BONUS);
        assertThat(manager.payAmount())
                .isEqualTo(SALARY+BONUS);
    }
}