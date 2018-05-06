package com.zeral.service;

import com.zeral.domain.ProjectType;

import com.zeral.repository.ProjectTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by karen on 2018/1/25.
 * */
@Service
@Transactional
public class ProjectTypeService {
    private final Logger log = LoggerFactory.getLogger(ProjectTypeService.class);
    private final Boolean IS_DELETE = false;
    private final ProjectTypeRepository projectTypeRepository;
    public ProjectTypeService(ProjectTypeRepository projectTypeRepository) {
        this.projectTypeRepository = projectTypeRepository;
    }
    /***
     * 获取列表(分页)
     * @param pageable 分页
     * @return Purchase
     */
    @Transactional(readOnly = true)
    public Page<ProjectType> getAllProjectType(Pageable pageable) {
        return projectTypeRepository.findAllByDeleteStatueIsTrue(pageable);
    }
    /**
     * 更新模板类型
     * */
    public Optional<ProjectType> updateProjectType(ProjectType projectType) {
        return Optional.of(projectTypeRepository.findById(projectType.getId())).map(projectTypeMain -> {
            projectTypeMain.setProjectTypeName(projectType.getProjectTypeName());
            projectTypeMain.setDes(projectType.getDes());
            return projectTypeMain;
        });
    }
    /**
     * 逻辑删除模板类型
     * */
    public Optional<ProjectType> deleteProjectType(Long id) {
        return Optional.of(projectTypeRepository.findById(id)).map(projectTypeMain -> {
            projectTypeMain.setDeleteStatue(IS_DELETE);
            return projectTypeMain;
        });
    }
}
