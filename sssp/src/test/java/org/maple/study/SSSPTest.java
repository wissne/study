/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.maple.study;

import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maple.study.entity.Department;
import org.maple.study.entity.Employee;
import org.maple.study.service.DepartmentService;
import org.maple.study.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * sssp : org.maple.study
 * Created by AUROGON on 2015/12/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SSSPTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    @Transactional
    public void testInsertBucketRecord4Test() {
        List<Department> departments = new ArrayList<>();
        Department department = null;
        for (int i = 0; i < 5; i++) {
            String dep = "部门";
            department = new Department();
            department.setDepartmentName(dep + (i + 1));
            department.setCreateTime(new Date());
            department.setLastUpdateTime(new Date());
            departments.add(department);
        }
        departmentService.saveAll(departments);

        List<Employee> employees = new ArrayList<>();
        Employee employee = null;
        for (int i = 0; i < 25; i++) {
            employee = new Employee();
            String curStr = String.valueOf(Character.toChars('A' + i));
            employee.setLastName(curStr + curStr);
            employee.setBirth(new Date());
            employee.setCreateTime(new Date());
            employee.setLastUpdateTime(new Date());
            employee.setEmail(curStr + curStr + "@qq.com");
            employee.setDepartment(departments.get(i % 5));
            employees.add(employee);
        }
        employeeService.saveAll(employees);
    }

    @Test
    public void testString() {
        for (int i = 0; i < 25; i++) {
            System.out.println(Character.toChars('A' + i));
        }
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        employeeService.removeAll();
        departmentService.removeAll();
    }

    @Test
    public void testDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testRepositorySecondLevelCache() {
        List<Department> departments = departmentService.findAll();
        departments = departmentService.findAll();
    }

    /**
     * Spring data 的二级缓存
     */
    @Test
    @Transactional
    public void testRepositorySecondLevelCache2() {
        List<Department> departments = departmentService.getAllCached();
        departments = departmentService.getAllCached();
    }

    /**
     * Fail to execute
     */
    @Test
    @Transactional
    public void testJpaSecondLevelCache() {
        String jpql = "select d from Department d";
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(jpql);
        System.out.println("Query: ");
        List<Department> departments = query.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
        entityManager.close();
        entityManager = entityManagerFactory.createEntityManager();
        query = entityManager.createQuery(jpql);
        System.out.println("Query: ");
        departments = query.setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
        entityManager.close();
    }
}
