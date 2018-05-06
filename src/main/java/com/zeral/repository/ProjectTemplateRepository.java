package com.zeral.repository;

import com.zeral.domain.ProjectTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karen on 2018/1/25.
 * */
@Repository
public interface ProjectTemplateRepository extends JpaRepository<ProjectTemplate, Long> {
    Page<ProjectTemplate> findByProjectTypeIdAndAndDeleteStatueIsTrue(String query, Pageable pageable);
}
