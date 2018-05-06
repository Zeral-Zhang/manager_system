package com.zeral.repository;

import com.zeral.domain.Supplier;
import com.zeral.domain.Units;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findAllByNameLikeAndStateIsTrue(String query, Pageable pageable);

    @Query(value="SELECT *FROM supplier WHERE state = 1 ORDER BY create_date",
        nativeQuery = true)
    List<Supplier> findAllSupplier();
}
