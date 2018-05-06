package com.zeral.web.rest;

import com.zeral.domain.Units;
import com.zeral.service.UnitsService;
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
public class UnitsResource {

    private final Logger log = LoggerFactory.getLogger(UnitsResource.class);

    private static final String ENTITY_NAME = "units";

    private final UnitsService unitsService;

    public UnitsResource(UnitsService unitsService) {
        this.unitsService = unitsService;
    }

    /***
     * POST  /departments : 新建单位
     * @param units
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/units")
    public ResponseEntity<Units> createUnits(@Valid @RequestBody Units units) throws URISyntaxException {
        Units result = unitsService.save(units);
        return ResponseEntity.created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增单位成功！", result.getName()))
            .body(result);
    }

    /***
     * PUT  /departments :更新部门树
     * @param units
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/units")
    public ResponseEntity<Units> updateUnits(@Valid @RequestBody Units units) throws URISyntaxException {
        if (units.getId() == null) {
            return createUnits(units);
        }
        Units result = unitsService.update(units.getId(), units);
        return ResponseEntity.created(new URI("/api/departmentsTree/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新单位成功！", result.getName()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取单位
     * @param id
     * @return
     */
    @GetMapping("/units/{id}")
    public ResponseEntity<Units> getUnits(@PathVariable Long id) {
        log.debug("REST request to get Units : {}", id);
        Units units = unitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(units));
    }

    /***
     * DELETE  /departments/:id :根据ID删除部门
     * @param id
     * @return
     */
    @DeleteMapping("/units/{id}")
    public ResponseEntity<Void> deleteUnits(@PathVariable Long id) {
        log.debug("REST request to delete Units : {}", id);
        unitsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除单位成功", id.toString())).build();
    }


    /***
     * 获取所有单位列表
     * @return
     */

    @GetMapping("/units")
    public ResponseEntity<List<Units>> getAllDepartments(String query, Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        String name = '%' + query + '%';
        Page<Units> page = unitsService.getAllUnits(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
