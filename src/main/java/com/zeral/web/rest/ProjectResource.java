package com.zeral.web.rest;
import com.zeral.domain.Project;
import com.zeral.domain.WorkPlan;
import com.zeral.service.ProjectService;
import com.zeral.service.WorkPlanService;
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

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

/**
 * Created by karen on 2018/1/29.
 * */
@RestController
@RequestMapping("/api")
public class ProjectResource {
    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);
    private final ProjectService projectService;
    private final WorkPlanService workPlanService;
    public ProjectResource(ProjectService projectService, WorkPlanService workPlanService) {
        this.projectService = projectService;
        this.workPlanService =  workPlanService;
    }

    /***
     * 分页获取
     *
     * @param pageable
     * @return
     */
    @GetMapping("/project-pager")
    public ResponseEntity<List<Project>> findAllProjectForPager(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Project");
        String name = '%' + query + '%';
        final Page<Project> page = projectService.getAllProjectForPage(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-pager");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> findProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectService.getProject(id);
        project.setPlanName(project.getProjectPlan().getName());
        project.setWorkPlanName(project.getSimpleName()+"整体计划");
        project.setPrincipalView(project.getUser().getDepartment().getName() +"—"+project.getUser().getName());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(project));
    }

    /***
     * 分页获取项目进度列表
     *
     * @param pageable
     * @return
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> findAllProjects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Project");
        String name = '%' + query + '%';
        Page<Project> page = projectService.getAllProjectForPage(name, pageable);
        List<Project> lst = page.getContent();
        for (Project pro:lst) {
            String id = pro.getId()+"";
            List<WorkPlan> successPlans = workPlanService.getAllByProIdSuccessPlan(id);
            List<WorkPlan> falsePlans = workPlanService.getAllByProIdFalsePlan(id);
            String result;
            if(falsePlans.size() == 0) {
                result = "100";
            } else {
                int success = successPlans.size();
                int falses = falsePlans.size();
                // 创建一个数值格式化对象
                NumberFormat numberFormat = NumberFormat.getInstance();
                // 设置精确到小数点后1位
                numberFormat.setMaximumFractionDigits(1);
                result = numberFormat.format((float) success / (float) (success + falses) * 100);
            }
            Float temp = Float.parseFloat(result);
            pro.setPercent(temp);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
