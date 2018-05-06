package com.zeral.service;

import com.zeral.bean.NgxTree;
import com.zeral.bean.Tree;
import com.zeral.bean.TreeOfTree;
import com.zeral.domain.*;
import com.zeral.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public List<Department> findByParentId(Long parentId){
        return departmentRepository.findByParentId(parentId);
    }

    /**
     * 保存
     *
     * @param treeOfTree the entity to save
     * @return the persisted entity
     */
    public Department saveDepartment(TreeOfTree treeOfTree) {
        Long l=new Long((long)0);
        Department depart = new Department();
        if (treeOfTree.getId() == null) {
            depart.setParentId(l);
        } else {
            depart.setParentId(treeOfTree.getId());
        }
        depart.setCreateTime(new Date());
        depart.setName(treeOfTree.getName());
        depart.setHasSon(false);
        departmentRepository.save(depart);
        log.debug("REST request to save Department : {}", depart);
        if (treeOfTree.getId() != null && treeOfTree.getId().longValue() != l.longValue()) {
            Department department = departmentRepository.findOne(treeOfTree.getId());
            department.setHasSon(true);
        }
        return depart;
    }


    /***
     * 更新部门
     * @param id
     * @return
     */
    public Department updateDepartment(Long id,Department department) {
        log.debug("REST request to update Department : {}", department);
        Department department1 = departmentRepository.findOne(id);
        department1.setName(department.getName());
        return department1;
    }

    /**
     * 获取所有的部门
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Department> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll(pageable);
    }

    /**
     * 根据Id获取部门
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Department findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findOne(id);
    }

    /**
     * 删除部门
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.delete(id);
    }

    /***
     * 获取所有的部门树
     * @return
     */
    public List<Tree> findDepartmentsTree() {
        return getLevelsDepartments(departmentRepository.findAll(), (long) 0);
    }

    public List<NgxTree> findDepartmentsNgxTree() {
        return getDepartmentsTree(departmentRepository.findAll(), (long) 0);
    }

    /**
     * 层级获取部门
     *
     * @param originDepartments
     * @param parentId
     * @return
     */
    private List<Tree> getLevelsDepartments(List<Department> originDepartments, Long parentId) {
        if (CollectionUtils.isEmpty(originDepartments)) {
            return null;
        }
        List<Tree> childDepartments = new ArrayList<>();
        for (Department parentDepartment : originDepartments) {
            Tree treeOfTree = new Tree();
            if (parentDepartment.getParentId().longValue() == parentId.longValue()) {
                treeOfTree.setName(parentDepartment.getName());
                treeOfTree.setId(parentDepartment.getId());
                childDepartments.add(treeOfTree);
                treeOfTree.setChildren(getLevelsDepartments(originDepartments, parentDepartment.getId()));
            }
        }
        return CollectionUtils.isEmpty(childDepartments) ? null : childDepartments;
    }

    /**
     * 层级获取部门
     *
     * @param originDepartments
     * @param parentId
     * @return
     */
    private List<NgxTree> getDepartmentsTree(List<Department> originDepartments, Long parentId) {
        if (CollectionUtils.isEmpty(originDepartments)) {
            return null;
        }
        List<NgxTree> childDepartments = new ArrayList<>();
        for (Department parentDepartment : originDepartments) {
            NgxTree treeOfTree = new NgxTree();
            if (parentDepartment.getParentId().longValue() == parentId.longValue()) {
                treeOfTree.setText(parentDepartment.getName());
                treeOfTree.setValue(parentDepartment.getId());
                childDepartments.add(treeOfTree);
                treeOfTree.setChildren(getDepartmentsTree(originDepartments, parentDepartment.getId()));
            }
        }
        return CollectionUtils.isEmpty(childDepartments) ? null : childDepartments;
    }
}
