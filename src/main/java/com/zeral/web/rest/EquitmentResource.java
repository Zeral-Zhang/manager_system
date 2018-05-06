package com.zeral.web.rest;

import com.zeral.domain.Equipment;
import com.zeral.service.EquipmentService;
import com.zeral.service.EquipmentService;
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
public class EquitmentResource {

    private final Logger log = LoggerFactory.getLogger(EquitmentResource.class);

    private static final String ENTITY_NAME = "units";

    private final EquipmentService equipmentService;

    public EquitmentResource(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /***
     * 新建
     * @param equipment 设备
     * @return Equipment
     * @throws URISyntaxException 异常
     */
    @PostMapping("/equipment")
    public ResponseEntity<Equipment> create(@Valid @RequestBody Equipment equipment) throws URISyntaxException {
        Equipment result = equipmentService.save(equipment);
        return ResponseEntity.created(new URI("/api/equipment/" + result.getId()))
            .headers(HeaderUtil.createAlert("新增设备成功！", result.getName()))
            .body(result);
    }

    /***
     * 更新
     * @param equipment 设备
     * @return Equipment
     * @throws URISyntaxException 异常
     */
    @PutMapping("/equipment")
    public ResponseEntity<Equipment> update(@Valid @RequestBody Equipment equipment) throws URISyntaxException {
        if (equipment.getId() == null) {
            return create(equipment);
        }
        Equipment result = equipmentService.update(equipment.getId(), equipment);
        return ResponseEntity.created(new URI("/api/equipment/" + result.getName()))
            .headers(HeaderUtil.createAlert("更新设备成功！", result.getName()))
            .body(result);
    }


    /***
     * GET  /units/:id : 根据ID 获取设备
     * @param id
     * @return
     */
    @GetMapping("/equipment/{id}")
    public ResponseEntity<Equipment> getEquipment(@PathVariable Long id) {
        log.debug("REST request to get Equipment : {}", id);
        Equipment equipment = equipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equipment));
    }

    /***
     * DELETE  /supplier/:id :根据ID删除设备
     * @param id
     * @return
     */
    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        log.debug("REST request to delete Equipment : {}", id);
        equipmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除设备成功", id.toString())).build();
    }


    /***
     * 获取所有设备列表
     * @return
     */

    @GetMapping("/equipment")
    public ResponseEntity<List<Equipment>> getAllEquipment(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Equipment");
        String name = '%' + query + '%';
        Page<Equipment> page = equipmentService.getAllEquipment(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/equipment");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /***
     * 根据供应商获取所有设备列表
     * @return
     */

    @GetMapping("/equipmentSupplier")
    public ResponseEntity<List<Equipment>> getAllEquipmentBySupplier(@RequestParam String query) {
        log.debug("REST request to get a page of Equipment");
        List<Equipment> result = equipmentService.getAllEquipmentNoPage(query);
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }

}
