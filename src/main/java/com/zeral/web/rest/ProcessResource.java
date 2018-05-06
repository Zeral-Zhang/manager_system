package com.zeral.web.rest;

import com.zeral.bean.Tree;
import com.zeral.domain.Process;
import com.zeral.service.ProcessService;
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
 * REST controller for managing Process.
 */
@RestController
@RequestMapping("/api")
public class ProcessResource {

    private final Logger log = LoggerFactory.getLogger(ProcessResource.class);

    private static final String ENTITY_NAME = "process";

    private final ProcessService processService;

    public ProcessResource(ProcessService processService) {
        this.processService = processService;
    }

    /***
     * POST  /process : 新建工序
     * @param process
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/process")
    public ResponseEntity<Process> createProcess(@RequestBody Process process) throws URISyntaxException {
        Process process1 = processService.findByName(process.getName());
        if (process1 != null) {
            throw new BadRequestAlertException("该工序已存在！", "processMangement", "hasSon");
        }
        Process result = processService.save(process);
        return ResponseEntity.created(new URI("/api/process/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增工序成功！", result.getName()))
            .body(result);
    }

    /***
     * PUT  /processs :更新工序树
     * @param process
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/process")
    public ResponseEntity<Process> updateProcess(@Valid @RequestBody Process process) throws URISyntaxException {
        Process process1 = processService.findByName(process.getName());
        if (process1 == null || process1.getId() == process.getId()) {
            Process result = processService.update(process.getId(), process);
            return ResponseEntity.created(new URI("/api/process/" + result.getName()))
                .headers(HeaderUtil.createAlert("更新工序成功！", result.getName()))
                .body(result);
        } else {
            throw new BadRequestAlertException("该工序已存在！", "processMangement", "hasSon");
        }
    }


    /***
     * DELETE  /processs/:id :根据ID删除工序
     * @param id
     * @return
     */
    @DeleteMapping("/process/{id}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        log.debug("REST request to delete Process : {}", id);
        List<Process> processList = processService.getProcessByParentId(id);
        if (processList != null && processList.size() > 0) {
            for (Process process: processList) {
                processService.delete(process.getId());
            }
        }
        processService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除工序成功", id.toString())).build();
    }

    /***
     * 获取工序树
     * @param
     * @return
     */
    @GetMapping("/processTree")
    public ResponseEntity<List<Tree>> getAllProcesss() {
        log.debug("REST request to get a page of Processs");
        List<Tree> tree = processService.findProcessTree();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tree));
    }


    /***
     * GET  /processs/:id : 根据ID 获取工序
     * @param id
     * @return
     */
    @GetMapping("/process/{id}")
    public ResponseEntity<Process> getProcess(@PathVariable Long id) {
        log.debug("REST request to get Process : {}", id);
        Process process = processService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(process));
    }


    /***
     * 获取工序列表(分页)
     * @return
     */
    @GetMapping("/process")
    public ResponseEntity<List<Process>> getAllProcesss(String query, Pageable pageable) {
        log.debug("REST request to get a page of Processs");
        String name = '%' + query + '%';
        Page<Process> page = processService.getAllProcess(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /***
     * 获取工序列表(不分页)
     * @param id
     * @return
     */
    @GetMapping("/processNoPage")
    public ResponseEntity<List<Process>> getAllProcesssNoPage(Long id) {
        log.debug("REST request to get a page of Processs");
        List<Process> processList = processService.getProcess();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processList));
    }

    @GetMapping("/processNoPage/{name}")
    public ResponseEntity<List<Process>> getAllProcesssNoPage(@PathVariable String name) {
        log.debug("REST request to get a page of Processs");
        String finalName = '%' + name + '%';
        List<Process> processList = processService.getProcessNameLike(finalName);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processList));
    }
    /***
     * 获取父工序列表(分页)
     * @return
     */

    @GetMapping("/processById")
    public ResponseEntity<List<Process>> getAllProcesssByParentId(Long id, Pageable pageable) {
        log.debug("REST request to get a page of Processs");
        Page<Process> page = processService.getAllProcessByParentId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /***
     * 根据父ID获取子工序
     * @param id
     * @return
     */
    @GetMapping("/childProcessNoPage")
    public ResponseEntity<List<Process>> getAllChildProcesssNoPage(Long id) {
        log.debug("REST request to get a page of Processs");
        List<Process> processList = processService.getProcessByParentId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processList));
    }


}
