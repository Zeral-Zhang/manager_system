package com.zeral.web.rest;

import com.zeral.domain.Inventory;
import com.zeral.service.InventoryService;
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
 * Created by karen on 2018/1/29.
 * */
@RestController
@RequestMapping("/api")
public class InventoryResource {
    private final Logger log = LoggerFactory.getLogger(InventoryResource.class);
    private final InventoryService inventoryService;
    public InventoryResource(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    /***
     * 分页获取
     *
     * @param pageable
     * @return
     */
    @GetMapping("/inventory-pager")
    public ResponseEntity<List<Inventory>> findAllProjectForPager(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Inventory");
        final Page<Inventory> page = inventoryService.getAllInventoryForPage(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventory-pager");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}

