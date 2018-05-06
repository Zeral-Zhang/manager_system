package com.zeral.repository;

import com.zeral.domain.Purchase;
import com.zeral.domain.Supplier;
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
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findAllBySupplierStateAndNameLikeAndStateIsTrue(boolean state, String query, Pageable pageable);
    List<Purchase> findAllBySupplierIdAndStateIsTrueOrderByCreateTime(String query);
}
