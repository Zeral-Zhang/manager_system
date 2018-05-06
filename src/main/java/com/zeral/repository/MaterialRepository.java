package com.zeral.repository;

import com.zeral.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findAllBySupplierStateAndTypeLikeAndNameLikeAndStateIsTrue(boolean state, String type, String query, Pageable pageable);
    Page<Material> findAllBySupplierStateAndNameLikeAndStateIsTrue(boolean state,  String query, Pageable pageable);
    List<Material> findAllBySupplierIdAndStateIsTrueOrderByCreateTime(String query);
}
