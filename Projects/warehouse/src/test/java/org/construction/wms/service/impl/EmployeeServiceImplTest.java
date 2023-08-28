package org.construction.wms.service.impl;

import org.construction.wms.domain.Employee;
import org.construction.wms.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmployeeServiceImplTest {
    @Autowired
    public EmployeeService employeeService;
    @Test
    public void testSave(){
        Employee employee = new Employee();
        employee.setAdmin(true);
        employee.setPassword("123456");
        employee.setAge(22);
        employee.setName("admin");
        employeeService.save(employee);
    }
}
