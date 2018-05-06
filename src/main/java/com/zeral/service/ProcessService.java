package com.zeral.service;

import com.zeral.bean.Tree;
import com.zeral.domain.Process;
import com.zeral.repository.ProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Service Implementation for managing Process.
 */
@Service
@Transactional
public class ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    /***
     * 保存工序
     * @param process
     * @return
     */
    public Process save(Process process) {
        log.debug("Request to save Process : {}", process);
        Process process1 = processRepository.findOne(process.getParentId());
        if (process1 != null) {
            process.setParentProcess(process1);
        }
        process.setCreateTime(new Date());
        process.setDeleteStatus(false);
        return processRepository.save(process);
    }

    /***
     * 更新工序
     * @param id
     * @return
     */
    public Process update(Long id, Process process) {
        log.debug("REST request to update Process : {}", process);
        Process process1 = processRepository.findOne(id);
        process1.setParentId(process.getParentId());
        process1.setName(process.getName());
        process1.setCreateTime(new Date());
        return process1;
    }

    /**
     * 删除工序
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Process : {}", id);
        Process process1 = processRepository.findOne(id);
        process1.setDeleteStatus(true);
    }

    /***
     * 根据父ID获取工序列表(分页)（当父ID为0时查询父工序，其他按照ID查询子工序）
     * @param parentId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Process> getAllProcessByParentId(Long parentId, Pageable pageable) {
        return processRepository.findAllByParentIdAndDeleteStatusIsFalseOrderByCreateTime(parentId, pageable);
    }

    /***
     * 获取工序列表(分页)
     * @param name
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Process> getAllProcess(String name, Pageable pageable) {
        return processRepository.findAllByNameLikeAndDeleteStatusIsFalseOrderByParentId(name, pageable);
    }


    /***
     * 根据ID获取该工序的子工序
     * @param parentId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Process> getProcessByParentId(Long parentId) {
        return processRepository.findByParentIdAndDeleteStatusIsFalse(parentId);
    }


    /***
     * 获取工序列表
     * @return
     */
    @Transactional(readOnly = true)
    public List<Process> getProcess() {
        return processRepository.findAllByDeleteStatusIsFalse();
    }

    @Transactional(readOnly = true)
    public List<Process> getProcessNameLike(String name) {
        return processRepository.findAllByDeleteStatusIsFalseAndNameLike(name);
    }
    /***
     * 根据ID获取工序
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Process findOne(Long id) {
        return processRepository.findOne(id);
    }

    /***
     * 根据工序名称查询工序
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public Process findByName(String name) {
        return processRepository.findByNameAndDeleteStatusIsFalse(name);
    }

    /***
     * 获取工序树
     * @return
     */
    @Transactional(readOnly = true)
    public List<Tree> findProcessTree() {
        return getLevelsProcess(processRepository.findAll(), (long) 0);
    }


    /**
     * 层级获取工序
     *
     * @param originProcesss
     * @param parentId
     * @return
     */
    private List<Tree> getLevelsProcess(List<Process> originProcesss, Long parentId) {
        if (CollectionUtils.isEmpty(originProcesss)) {
            return null;
        }
        List<Tree> childProcess = new ArrayList<>();
        for (Process parentProcess : originProcesss) {
            Tree treeOfTree = new Tree();
            if (parentProcess.getParentId() == parentId) {
                treeOfTree.setName(parentProcess.getName());
                treeOfTree.setId(parentProcess.getId());
                childProcess.add(treeOfTree);
                treeOfTree.setChildren(getLevelsProcess(originProcesss, parentProcess.getId()));
            }
        }
        return CollectionUtils.isEmpty(childProcess) ? null : childProcess;
    }
}
