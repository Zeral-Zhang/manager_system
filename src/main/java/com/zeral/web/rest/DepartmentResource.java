package com.zeral.web.rest;

import com.zeral.bean.NgxTree;
import com.zeral.bean.Tree;
import com.zeral.bean.TreeOfTree;
import com.zeral.domain.*;
import com.zeral.service.DepartmentService;
import com.zeral.service.UserService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private final DepartmentService departmentService;
    private UserService userService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /***A
     * POST  /departments : 新建部门
     * @param treeOfTree
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/departmentsTree")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody TreeOfTree treeOfTree) throws URISyntaxException {
        Department result = departmentService.saveDepartment(treeOfTree);
        return ResponseEntity.created(new URI("/api/departmentsTree/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增部门成功！", result.getName()))
            .body(result);
    }

    /***
     * PUT  /departments :更新部门树
     * @param treeOfTree
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/departmentsTree")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody TreeOfTree treeOfTree) throws URISyntaxException {
        if (treeOfTree.getId() == null) {
            return createDepartment(treeOfTree);
        }
        Department department = new Department();
        department.setName(treeOfTree.getName());
        Department result = departmentService.updateDepartment(treeOfTree.getId(), department);
        return ResponseEntity.created(new URI("/api/departmentsTree/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新部门成功！", result.getName()))
            .body(result);
    }

    /***
     * 获取部门树
     * @param
     * @return
     */
    @GetMapping("/departmentsTree")
    public ResponseEntity<List<Tree>> getAllDepartments() {
        log.debug("REST request to get a page of Departments");
        List<Tree> tree = departmentService.findDepartmentsTree();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tree));
    }

    /***
     * 获取部门树
     * @param
     * @return
     */
    @GetMapping("/departmentsNgxTree")
    public ResponseEntity<List<NgxTree>> getDepartmentsTree() {
        List<NgxTree> tree = departmentService.findDepartmentsNgxTree();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tree));
    }


    /***
     * GET  /departments/:id : 根据ID 获取部门树
     * @param id
     * @return
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Department department = departmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(department));
    }

    /***
     * DELETE  /departments/:id :根据ID删除部门
     * @param id
     * @return
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        List<Department> departmentList = departmentService.findByParentId(id);
        if(departmentList!=null&&departmentList.size()>0){
            throw new BadRequestAlertException("请删除子部门", "userManagement", "hasSon");
        }
        departmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "删除部门成功", id.toString())).build();
    }


    /***
     * 获取所有部门列表
     * @return
     */

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        Page<Department> page = departmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /***
     * 更新部门
     * @param department
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/departments")
    public ResponseEntity<Department> updateRole(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update RequestBody : {}", department);
        Department result = departmentService.updateDepartment(department.getId(), department);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, department.getName()))
            .body(result);
    }

}
