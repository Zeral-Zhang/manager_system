package com.zeral.web.rest;

import com.zeral.domain.WorkPlan;
import com.zeral.service.WorkPlanService;
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
 * Created by karen on 2018/1/30.
 * */
@RestController
@RequestMapping("/api")
public class WorkplanResource {
    private final Logger log = LoggerFactory.getLogger(WorkplanResource.class);
    private final WorkPlanService workPlanService;
    public WorkplanResource(WorkPlanService workPlanService){
        this.workPlanService  = workPlanService;
    }
    /**
     * 项目所有完成事件分页
     * */
    @GetMapping("/successful-workPlans")
    public ResponseEntity<List<WorkPlan>> findAllSuccessfulProjects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of WorkPlan");
        String name = '%' + query + '%';
        final Page<WorkPlan> page = workPlanService.getAllSuccess(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/successful-workPlans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * 项目所有未完成事件分页
     * */
    @GetMapping("/false-workPlans")
    public ResponseEntity<List<WorkPlan>> findAllFalseProjects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of WorkPlan");
        String name = '%' + query + '%';
        final Page<WorkPlan> page = workPlanService.getAllFalse(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/false-workPlans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     *一个项目按部门查询的所有未完成事件
     */
    @GetMapping("/false-workPlansOfPro")
    public ResponseEntity<List<WorkPlan>> findAllFalsePlansOfPro(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of WorkPlan");
        final Page<WorkPlan> page = workPlanService.getAllFalsePlanOfProject(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/false-workPlansOfPro");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     *一个项目按部门查询的所有已完成事件
     */
    @GetMapping("/success-workPlansOfPro")
    public ResponseEntity<List<WorkPlan>> findAllSuccessPlansOfPro(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of WorkPlan");
        final Page<WorkPlan> page = workPlanService.getAllSuccessPlanOfProject(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/success-workPlansOfPro");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
