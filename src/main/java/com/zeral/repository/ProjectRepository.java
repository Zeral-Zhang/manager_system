package com.zeral.repository;

import com.zeral.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karen on 2018/1/29.
 * */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByDeleteStatueIsTrueAndAndSimpleNameLike(String query, Pageable pageable);

    @EntityGraph(attributePaths = "participants")
    Project findById(Long id);
}
