package com.zeral.web.rest;

import com.zeral.domain.Supplier;
import com.zeral.service.SupplierService;
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
public class SupplierResource {

    private final Logger log = LoggerFactory.getLogger(SupplierResource.class);

    private static final String ENTITY_NAME = "units";

    private final SupplierService supplierService;

    public SupplierResource(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /***
     * POST  /supplier : 新建
     * @param supplier
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/supplier")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) throws URISyntaxException {
        Supplier result = supplierService.save(supplier);
        return ResponseEntity.created(new URI("/api/supplier/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增供应商成功！", result.getName()))
            .body(result);
    }

    /***
     * PUT  /supplier :更新
     * @param supplier
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/supplier")
    public ResponseEntity<Supplier> updateSupplier(@Valid @RequestBody Supplier supplier) throws URISyntaxException {
        if (supplier.getId() == null) {
            return createSupplier(supplier);
        }
        Supplier result = supplierService.update(supplier.getId(), supplier);
        return ResponseEntity.created(new URI("/api/supplier/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新供应商成功！", result.getName()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取供应商
     * @param id
     * @return
     */
    @GetMapping("/supplier/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long id) {
        log.debug("REST request to get Supplier : {}", id);
        Supplier supplier = supplierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(supplier));
    }

    /***
     * DELETE  /supplier/:id :根据ID删除供应商
     * @param id
     * @return
     */
    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        log.debug("REST request to delete Supplier : {}", id);
        supplierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除供应商成功", id.toString())).build();
    }


    /***
     * 获取所有供应商列表
     * @return
     */

    @GetMapping("/supplier")
    public ResponseEntity<List<Supplier>> getAllSupplier(String query, Pageable pageable) {
        log.debug("REST request to get a page of Supplier");
        String name = '%' + query + '%';
        Page<Supplier> page = supplierService.getAllSupplier(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supplier");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /***
     * 获取所有供应商列表
     * @return
     */

    @GetMapping("/supplierNoPage")
    public ResponseEntity<List<Supplier>> getAllSupplierNoPage() {
        List<Supplier> result = supplierService.getAllSupplierNoPage();
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

}
