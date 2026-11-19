package com.rosewood.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class InMemoryEmployeeRepositoryTest {

    @Test
    void findsExistingEmployeeById() {
        InMemoryEmployeeRepository repository = new InMemoryEmployeeRepository();

        Employee employee = repository.findById("e1");

        assertNotNull(employee);
        assertEquals("e1", employee.getId());
        assertEquals("Efua Sarpong", employee.getName());
    }

    @Test
    void findsSecondExistingEmployeeById() {
        InMemoryEmployeeRepository repository = new InMemoryEmployeeRepository();

        Employee employee = repository.findById("e2");

        assertNotNull(employee);
        assertEquals("e2", employee.getId());
        assertEquals("Kwame Owusu", employee.getName());
    }

    @Test
    void returnsNullWhenEmployeeDoesNotExist() {
        InMemoryEmployeeRepository repository = new InMemoryEmployeeRepository();

        Employee employee = repository.findById("unknown");

        assertNull(employee);
    }
}