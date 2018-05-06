package com.zeral.web.rest;

import com.zeral.domain.ProjectType;
import com.zeral.repository.ProjectTypeRepository;
import com.zeral.service.ProjectTypeService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by karen on 2018/1/25.
 * */
@RestController
@RequestMapping("/api")
public class ProjectTypeResource {
    private final Logger log = LoggerFactory.getLogger(ProfileInfoResource.class);
    private final String ENTITY_NAME = "projectType";
    private final Boolean NO_DELETE = true;
    private final ProjectTypeService projectTypeService;
    private final ProjectTypeRepository projectTypeRepository;
    public ProjectTypeResource(ProjectTypeService projectTypeService, ProjectTypeRepository projectTypeRepository) {
        this.projectTypeService = projectTypeService;
        this.projectTypeRepository = projectTypeRepository;
    }
    /***
     * 分页获取项目模板类型
     *@param query
     * @param pageable
     * @return
     */
    @GetMapping("projectTypes")
    public ResponseEntity<List<ProjectType>> getAllProject(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of ProjectType");
        final Page<ProjectType> page = projectTypeService.getAllProjectType(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projectTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * 新增模板类型
     * @param projectType
     * @return
     * */
    @PostMapping("projectType")
    public ResponseEntity<ProjectType> addProjectType(@Valid @RequestBody ProjectType projectType) throws URISyntaxException {
        log.debug("REST request to save ProjectType : {}", projectType);
        if(projectTypeRepository.findOneByProjectTypeNameIgnoreCase(projectType.getProjectTypeName()).isPresent()) {
            throw new BadRequestAlertException("该类型模板名称已被使用", ENTITY_NAME, "nameExists");
        }
        projectType.setDeleteStatue(NO_DELETE);
        ProjectType result  = projectTypeRepository.save(projectType);
        return ResponseEntity.created(new URI("/api/projectType/" + result.getProjectTypeName()))
            .headers(HeaderUtil.createAlert("新增模板类型成功！", result.getProjectTypeName()))
            .body(result);
    }
    /**
     * 修改模板类型
     * @param projectType
     * @return
     * */
    @PutMapping("projectType")
    public ResponseEntity<ProjectType> updateProjectType(@Valid @RequestBody ProjectType projectType) throws URISyntaxException {
        log.debug("REST request to update ProjectType : {}", projectType);
        Optional<ProjectType> typeOptional = projectTypeRepository.findOneByProjectTypeNameIgnoreCase(projectType.getProjectTypeName());
        if(typeOptional.isPresent() && typeOptional.get().getProjectTypeName().equals(projectType.getProjectTypeName())) {
            throw new BadRequestAlertException("模板类型名称已被使用", ENTITY_NAME, "nameExists");
        }
        Optional<ProjectType> typeOptional1 = projectTypeService.updateProjectType(projectType);
        return ResponseUtil.wrapOrNotFound(typeOptional1,
            HeaderUtil.createAlert("角色更新成功", projectType.getProjectTypeName()));
    }
    /**
     * 删除模板类型
     * */
    @DeleteMapping("/projectType/{id}")
    public ResponseEntity<ProjectType> deleteProjectType(@PathVariable String id) {
        log.debug("REST request to delete ProjectType : {}", id);
        Optional<ProjectType> newType = projectTypeService.deleteProjectType(Long.valueOf(id));
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "删除用户成功", newType.get().getProjectTypeName())).build();
    }
}
