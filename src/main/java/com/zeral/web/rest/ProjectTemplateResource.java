package com.zeral.web.rest;

import com.zeral.domain.ProjectTemplate;
import com.zeral.service.ProjectTemplateService;
import com.zeral.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by karen on 2018/1/25.
 * */
@RestController
@RequestMapping("/api")
public class ProjectTemplateResource {
    private final Logger log = LoggerFactory.getLogger(ProjectTemplateResource.class);
    private final ProjectTemplateService projectTemplateService;
    public ProjectTemplateResource(ProjectTemplateService projectTemplateService) {
        this.projectTemplateService = projectTemplateService;
    }
    /***
     * 分页获取项目模板列表
     *@param query
     * @param pageable
     * @return
     */
    @GetMapping("projectTemplates")
    public ResponseEntity<List<ProjectTemplate>> getAllProjectTemplate(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of ProjectTemplate");
        final Page<ProjectTemplate> page = projectTemplateService.getAllProjectTemplate(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projectTemplates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
