package com.zeral.service;

import com.zeral.domain.ProjectTemplate;
import com.zeral.repository.ProjectTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by karen on 2018/1/25.
 * */
@Service
@Transactional
public class ProjectTemplateService {
    private final Logger log = LoggerFactory.getLogger(ProjectTemplateService.class);
    private final ProjectTemplateRepository projectTemplateRepository;
    public  ProjectTemplateService(ProjectTemplateRepository projectTemplateRepository) {
        this.projectTemplateRepository = projectTemplateRepository;
    }
    /***
     * 获取列表(分页)
     * @param query 类型Id
     * @param pageable 分页
     * @return Purchase
     */
    @Transactional(readOnly = true)
    public Page<ProjectTemplate> getAllProjectTemplate(String query, Pageable pageable){
        return projectTemplateRepository.findByProjectTypeIdAndAndDeleteStatueIsTrue(query,pageable);
    }
}
