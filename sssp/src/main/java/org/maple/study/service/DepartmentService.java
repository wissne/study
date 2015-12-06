package org.maple.study.service;

import org.maple.study.entity.Department;
import org.maple.study.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by AUROGON on 2015/12/6.
 */
@Service
public class DepartmentService {

    @Autowired
    @Qualifier("departmentRepository")
    private DepartmentRepository departmentRepository;

    @Transactional
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Department> getAllCached() {
        return departmentRepository.getAllCached();
    }

    @Transactional
    public void removeAll() {
        departmentRepository.deleteAll();
    }

    @Transactional
    public void saveAll(List<Department> departments) {
        departmentRepository.save(departments);
    }
}
