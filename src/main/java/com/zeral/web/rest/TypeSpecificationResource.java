package com.zeral.web.rest;

import com.zeral.domain.TypeSpecification;
import com.zeral.constants.TypesWithPurchase;
import com.zeral.service.TypeSpecificationService;
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
public class TypeSpecificationResource {

    private final Logger log = LoggerFactory.getLogger(TypeSpecificationResource.class);

    private static final String ENTITY_NAME = "units";

    private final TypeSpecificationService typeSpecificationService;

    public TypeSpecificationResource(TypeSpecificationService typeSpecificationService) {
        this.typeSpecificationService = typeSpecificationService;
    }

    /***
     * 新建
     * @param typeSpecification 类型
     * @return TypeSpecification
     * @throws URISyntaxException 异常
     */
    @PostMapping("/typeSpecification")
    public ResponseEntity<TypeSpecification> create(@Valid @RequestBody TypeSpecification typeSpecification) throws URISyntaxException {
        TypeSpecification result = typeSpecificationService.save(typeSpecification);
        return ResponseEntity.created(new URI("/api/supplier/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增类型成功！", result.getContent()))
            .body(result);
    }

    /***
     * 更新
     * @param typeSpecification 类型
     * @return TypeSpecification
     * @throws URISyntaxException 异常
     */
    @PutMapping("/typeSpecification")
    public ResponseEntity<TypeSpecification> update(@Valid @RequestBody TypeSpecification typeSpecification) throws URISyntaxException {
        if (typeSpecification.getId() == null) {
            return create(typeSpecification);
        }
        TypeSpecification result = typeSpecificationService.update(typeSpecification.getId(), typeSpecification);
        return ResponseEntity.created(new URI("/api/typeSpecification/" + result.getContent()))
            .headers(HeaderUtil.createAlert("更新类型成功！", result.getContent()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取类型
     * @param id
     * @return
     */
    @GetMapping("/typeSpecification/{id}")
    public ResponseEntity<TypeSpecification> getTypeSpecification(@PathVariable Long id) {
        log.debug("REST request to get TypeSpecification : {}", id);
        TypeSpecification typeSpecification = typeSpecificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeSpecification));
    }

    /***
     * DELETE  /supplier/:id :根据ID删除类型
     * @param id
     * @return
     */
    @DeleteMapping("/typeSpecification/{id}")
    public ResponseEntity<Void> deleteTypeSpecification(@PathVariable Long id) {
        log.debug("REST request to delete TypeSpecification : {}", id);
        typeSpecificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除类型成功", id.toString())).build();
    }


    /***
     * 获取所有类型列表
     * @return
     */

    @GetMapping("/typeSpecification")
    public ResponseEntity<List<TypeSpecification>> getAllTypeSpecification(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of TypeSpecification");
        String name = '%' + query + '%';
        Page<TypeSpecification> page = typeSpecificationService.getAllTypeSpecification(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typeSpecification");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /***
     * 根据设备获取所有类型列表
     * @return
     */

    @GetMapping("/typeSpecificationSupplier")
    public ResponseEntity<List<TypeSpecification>> getAllTypeSpecificationBySupplier(@RequestParam String query) {
        log.debug("REST request to get a page of TypeSpecification");
        List<TypeSpecification> result = typeSpecificationService.getAllTypeSpecificationNoPage(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

    /***
     * 根据设备获取所有可选类型
     * @return
     */

    @GetMapping("/typeSpecificationSelect")
    public ResponseEntity<List<TypeSpecification>> getAllTypeBySelect(@RequestParam String query) {
        log.debug("REST request to get a page of TypeSpecification");
        List<TypeSpecification> result = typeSpecificationService.getAllTypeBySelect(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }


    /***
     * 获取所有已选中类型
     * @param query
     * @return
     */
    @GetMapping("/typeSpecificationSelected")
    public ResponseEntity<List<TypeSpecification>> getAllTypeBySelected(@RequestParam String query) {
        log.debug("REST request to get a page of TypeSpecification");
        List<TypeSpecification> result = typeSpecificationService.getAllTypeBySelect(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }




    /***
     * POST   /typeSpecificationWithPurchase :批量绑定设备类型
     * @param typesWithPurchase 混合
     * @return 返回
     */
    @PostMapping("/typeSpecificationWithEquipment")
    public ResponseEntity<Void> typeSpecificationWithEquipment(@RequestBody TypesWithPurchase typesWithPurchase) {
        Long[] typeIds = typesWithPurchase.getTypeIds();
        for(int i=0;i < typeIds.length; i++){
            typeSpecificationService.update(typeIds[i], typesWithPurchase.getEquipmentId());
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("绑定类型成功",typesWithPurchase.getEquipmentId())).build();
    }

}
