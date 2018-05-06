package com.zeral.web.rest;

import com.zeral.domain.Material;
import com.zeral.service.MaterialService;
import com.zeral.service.MaterialService;
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
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static final String ENTITY_NAME = "units";

    private final MaterialService materialService;

    public MaterialResource( MaterialService materialService) {
        this.materialService = materialService;
    }

    /***
     * 新建
     * @param material 材料
     * @return Material
     * @throws URISyntaxException 异常
     */
    @PostMapping("/material")
    public ResponseEntity<Material> create(@Valid @RequestBody Material material) throws URISyntaxException {
        Material result = materialService.save(material);
        return ResponseEntity.created(new URI("/api/material/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增材料成功！", result.getName()))
            .body(result);
    }

    /***
     * 更新
     * @param material 材料
     * @return Material
     * @throws URISyntaxException 异常
     */
    @PutMapping("/material")
    public ResponseEntity<Material> update(@Valid @RequestBody Material material) throws URISyntaxException {
        if (material.getId() == null) {
            return create(material);
        }
        Material result = materialService.update(material.getId(), material);
        return ResponseEntity.created(new URI("/api/material/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新材料成功！", result.getName()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取材料
     * @param id
     * @return
     */
    @GetMapping("/material/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Material material = materialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(material));
    }

    /***
     * DELETE  /supplier/:id :根据ID删除材料
     * @param id
     * @return
     */
    @DeleteMapping("/material/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除材料成功", id.toString())).build();
    }


    /***
     * 获取所有材料列表
     * @return
     */

    @GetMapping("/material")
    public ResponseEntity<List<Material>> getAllMaterial(@RequestParam String[] query, Pageable pageable) {
        log.debug("REST request to get a page of Material");
        String name = '%' + query[0] + '%';
        Page<Material> page = materialService.getAllMaterial(query[1],name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/material");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /***
     * 根据供应商获取所有材料列表
     * @return
     */

    @GetMapping("/materialSupplier")
    public ResponseEntity<List<Material>> getAllMaterialBySupplier(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Material");
        List<Material> result = materialService.getAllMaterialNoPage(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

}
