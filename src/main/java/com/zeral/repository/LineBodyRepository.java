package com.zeral.repository;

import com.zeral.domain.LineBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface LineBodyRepository extends JpaRepository<LineBody, Long> {
    Page<LineBody> findAllByDeleteStatueIsTrueAndNameLike(String query, Pageable pageable);
    LineBody findById(Long id);
    Optional<LineBody> findOneById(Long id);
    Optional<LineBody> findByNameIgnoreCase(String name);
    Optional<LineBody> findByCodeIgnoreCase(String code);
}
