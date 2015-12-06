package org.maple.study.repository;

import org.maple.study.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by AUROGON on 2015/12/6.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee getByLastName(String lastName);

}
