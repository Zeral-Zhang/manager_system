package com.zeral.service;

import com.zeral.domain.WorkPlan;
import com.zeral.repository.WorkPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by karen on 2018/1/30.
 * */
@Service
@Transactional
public class WorkPlanService {
    private final Logger log = LoggerFactory.getLogger(WorkPlanService.class);
    private final WorkPlanRepository workPlanRepository;
    public WorkPlanService(WorkPlanRepository workPlanRepository) {
        this.workPlanRepository = workPlanRepository;
    }
    /**
     *一个项目已完成计划分页查询
     */
    @Transactional(readOnly = true)
    public Page<WorkPlan> getAllByProIdSuccessPlan(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByProIdAndStateAndDeleteStatueIsTrue(query,1, p);
    }
    /**
     *一个项目未完成计划分页查询
     */
    @Transactional(readOnly = true)
    public Page<WorkPlan> getAllByProIdFalsePlan(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByProIdAndStateIsNotAndDeleteStatueIsTrue(query,1,p);
    }
    /**
     *根据项目查出已完成计划查询
     */
    @Transactional(readOnly = true)
    public List<WorkPlan> getAllByProIdSuccessPlan(String query) {
        return workPlanRepository.findAllByProIdAndStateAndDeleteStatueIsTrue(query,1,
            new Sort(Sort.Direction.ASC,"sort"));
    }
    /**
     *根据项目查出未完成计划查询
     */
    @Transactional(readOnly = true)
    public List<WorkPlan> getAllByProIdFalsePlan(String query) {
        return workPlanRepository.findAllByProIdAndStateIsNotAndDeleteStatueIsTrue(query,1,
            new Sort(Sort.Direction.ASC,"sort"));
    }

    /**
     *查出所有事件
     */
    @Transactional(readOnly = true)
    public Page<WorkPlan> getAll(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByDeleteStatueIsTrueAndNameLike(query, p);
    }
    /**
     *查出所有已完成事件
     */
    public Page<WorkPlan> getAllSuccess(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByDeleteStatueIsTrueAndStateAndNameLike(1, query,p);
    }
    /**
     *查出所有未完成事件
     */
    public Page<WorkPlan> getAllFalse(String query, Pageable pageable) {
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByDeleteStatueIsTrueAndStateIsNotAndNameLike(1, query,p);
    }
    /**
     *一个项目按部门查询的所有未完成事件
     */
    public Page<WorkPlan> getAllFalsePlanOfProject(String query, Pageable pageable) {
        String[] querys = query.split("!");
        String depId = querys[0];
        String proId = querys[1];
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByDeleteStatueIsTrueAndStateIsNotAndDepIdAndProId(1, depId, proId, p);
    }
    /**
     *一个项目按部门查询的所有已完成事件
     */
    public Page<WorkPlan> getAllSuccessPlanOfProject(String query, Pageable pageable) {
        String[] querys = query.split("!");
        String depId = querys[0];
        String proId = querys[1];
        Pageable p = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
            new Sort(Sort.DEFAULT_DIRECTION, "sort"));
        return workPlanRepository.findAllByDeleteStatueIsTrueAndStateAndDepIdAndProId(1, depId, proId, p);
    }
}

