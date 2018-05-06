package com.zeral.repository;

import com.zeral.domain.TypeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@Repository
public interface TypeSpecificationRepository extends JpaRepository<TypeSpecification, Long> {
    Page<TypeSpecification> findAllByContentLike(String query, Pageable pageable);

    List<TypeSpecification> findAllByEquipmentIdOrderByCreateTime(String query);

    List<TypeSpecification> findAllByEquipmentIdIsNullOrEquipmentIdOrderByCreateTime(String query);

}
