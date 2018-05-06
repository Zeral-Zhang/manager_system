package com.zeral.service;

import com.zeral.domain.Project;
import com.zeral.domain.User;
import com.zeral.domain.WorkPlan;
import com.zeral.repository.ProjectRepository;
import com.zeral.repository.WorkPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by karen on 2018/1/29.
 * */
@Service
@Transactional
public class ProjectService {
    private final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
    /***
     * 分页获取
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Project> getAllProjectForPage(String query, Pageable pageable) {
        log.debug("获取项目的分页数据");
        return projectRepository.findAllByDeleteStatueIsTrueAndAndSimpleNameLike(query, pageable);
    }
    /***
     * 分页获取项目列表，存在项目进度的项目列表
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Project> getAllProject(String query, Pageable pageable) {
        log.debug("获取项目的分页数据");
        return  projectRepository.findAllByDeleteStatueIsTrueAndAndSimpleNameLike(query, pageable);
    }
    public Project getProject(Long id) {
        Project  project = projectRepository.findById(id);
        return project;
    }
}
