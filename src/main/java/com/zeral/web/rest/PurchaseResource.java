package com.zeral.web.rest;

import com.zeral.domain.Purchase;
import com.zeral.domain.Supplier;
import com.zeral.service.PurchaseService;
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
public class PurchaseResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseResource.class);

    private static final String ENTITY_NAME = "units";

    private final PurchaseService purchaseService;

    public PurchaseResource(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /***
     * 新建
     * @param purchase 外购件
     * @return Purchase
     * @throws URISyntaxException 异常
     */
    @PostMapping("/purchase")
    public ResponseEntity<Purchase> create(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        Purchase result = purchaseService.save(purchase);
        return ResponseEntity.created(new URI("/api/purchase/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增外购件成功！", result.getName()))
            .body(result);
    }

    /***
     * 更新
     * @param purchase 外购件
     * @return Purchase
     * @throws URISyntaxException 异常
     */
    @PutMapping("/purchase")
    public ResponseEntity<Purchase> update(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        if (purchase.getId() == null) {
            return create(purchase);
        }
        Purchase result = purchaseService.update(purchase.getId(), purchase);
        return ResponseEntity.created(new URI("/api/purchase/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新外购件成功！", result.getName()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取外购件
     * @param id
     * @return
     */
    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        log.debug("REST request to get Purchase : {}", id);
        Purchase purchase = purchaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchase));
    }

    /***
     * DELETE  /supplier/:id :根据ID删除外购件
     * @param id
     * @return
     */
    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        log.debug("REST request to delete Purchase : {}", id);
        purchaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除外购件成功", id.toString())).build();
    }


    /***
     * 获取所有外购件列表
     * @return
     */

    @GetMapping("/purchase")
    public ResponseEntity<List<Purchase>> getAllPurchase(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Purchase");
        String name = '%' + query + '%';
        Page<Purchase> page = purchaseService.getAllPurchase(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /***
     * 根据供应商获取所有外购件列表
     * @return
     */

    @GetMapping("/purchaseSupplier")
    public ResponseEntity<List<Purchase>> getAllPurchaseBySupplier(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Purchase");
        List<Purchase> result = purchaseService.getAllPurchaseNoPage(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

}
