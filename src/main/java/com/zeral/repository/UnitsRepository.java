package com.zeral.repository;

import com.zeral.domain.Units;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {
    Page<Units> findAllByNameLike(String query, Pageable pageable);
}
