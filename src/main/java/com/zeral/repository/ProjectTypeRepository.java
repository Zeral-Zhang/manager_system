package com.zeral.repository;

import com.zeral.domain.ProjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by karen on 2018/1/25.
 * */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType,Long> {
    ProjectType findById(Long id);
    Page<ProjectType> findAllByDeleteStatueIsTrue(Pageable pageable);
    Optional<ProjectType> findOneByProjectTypeNameIgnoreCase(String projectName);
}
