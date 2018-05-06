package com.zeral.repository;

import com.zeral.domain.Equipment;
import com.zeral.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Page<Equipment> findAllBySupplierStateAndNameLikeAndStateIsTrue(boolean state, String query, Pageable pageable);
    List<Equipment> findAllBySupplierIdAndStateIsTrueOrderByCreateTime(String query);
}
