package com.zeral.service;

import com.zeral.domain.Equipment;
import com.zeral.repository.EquipmentRepository;
import com.zeral.repository.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentService.class);

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /***
     * 保存
     * @param equipment 设备
     * @return Equipment
     */
    public Equipment save(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        equipment.setState(true);
        equipment.setCreateTime(new Date());
        return equipmentRepository.save(equipment);
    }

    /***
     * 更新
     * @param id id
     * @return Equipment
     */
    public Equipment update(Long id, Equipment equipment) {
        log.debug("REST request to update Equipment : {}", equipment);
        Equipment equipment1 = equipmentRepository.findOne(id);
        equipment1.setName(equipment.getName());
        equipment1.setSupplierId(equipment.getSupplierId());
        return equipment1;
    }

    /**
     * 删除
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        Equipment equipment = equipmentRepository.findOne(id);
        equipment.setState(false);
    }

    /***
     * 获取列表(分页)
     * @param query 设备名称
     * @param pageable 分页
     * @return Equipment
     */
    @Transactional(readOnly = true)
    public Page<Equipment> getAllEquipment(String query, Pageable pageable) {
        return equipmentRepository.findAllBySupplierStateAndNameLikeAndStateIsTrue(true, query, pageable);
    }

    /***
     * 获取列表（根据厂商）
     * @param query 厂商ID
     * @return 分页
     */
    @Transactional(readOnly = true)
    public List<Equipment> getAllEquipmentNoPage(String query) {
        return equipmentRepository.findAllBySupplierIdAndStateIsTrueOrderByCreateTime(query);
    }


    /***
     * 根据ID获取
     * @param id ID
     * @return Equipment
     */
    @Transactional(readOnly = true)
    public Equipment findOne(Long id) {
        return equipmentRepository.findOne(id);
    }

}
