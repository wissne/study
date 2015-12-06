package org.maple.study.service;

import org.maple.study.entity.Employee;
import org.maple.study.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by AUROGON on 2015/12/6.
 */
@Service
public class EmployeeService {

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Transactional
    public Page<Employee> getPage(int pageNo, int pageSize) {
        PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
        return employeeRepository.findAll(pageable);
    }

    @Transactional
    public void saveAll(List<Employee> employees) {
        employeeRepository.save(employees);
    }

    @Transactional
    public void removeAll() {
        employeeRepository.deleteAll();;
    }

    @Transactional(readOnly = true)
    public Employee getByLastName(String lastName) {
        return employeeRepository.getByLastName(lastName);
    }

    @Transactional
    public void save(Employee employee) {
        if (employee.getId() == null) {
            employee.setCreateTime(new Date());
            employee.setLastUpdateTime(new Date());
        }
        employeeRepository.saveAndFlush(employee);
    }

    @Transactional(readOnly = true)
    public Employee get(Integer id) {
        return employeeRepository.findOne(id);
    }

    @Transactional
    public void delete(Integer id) {
        employeeRepository.delete(id);
    }
}
