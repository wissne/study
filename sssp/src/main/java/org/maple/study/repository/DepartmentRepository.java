package org.maple.study.repository;

import org.maple.study.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by AUROGON on 2015/12/6.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @SuppressWarnings("ALL")
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    @Query("select d from Department d")
    List<Department> getAllCached();
}
