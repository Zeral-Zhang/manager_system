package com.zeral.repository;

import com.zeral.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByParentId(Long parentId);

}
