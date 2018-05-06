package com.zeral.service;

import com.zeral.domain.Units;
import com.zeral.repository.UnitsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class UnitsService {

    private final Logger log = LoggerFactory.getLogger(UnitsService.class);

    private final UnitsRepository unitsRepository;

    public UnitsService(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    /***
     * 保存单位
     * @param units
     * @return
     */
    public Units save(Units units) {
        log.debug("Request to save Units : {}", units);
        units.setCreateTime(new Date());
        return unitsRepository.save(units);
    }

    /***
     * 更新单位
     * @param id
     * @return
     */
    public Units update(Long id, Units units) {
        log.debug("REST request to update Units : {}", units);
        Units units1 = unitsRepository.findOne(id);
        units1.setName(units.getName());
        units1.setCode(units.getCode());
        return units1;
    }

    /**
     * 删除单位
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Units : {}", id);
        unitsRepository.delete(id);
    }

    /***
     * 获取单位列表
     * @param query
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Units> getAllUnits(String query, Pageable pageable) {
        return unitsRepository.findAllByNameLike(query,pageable);
    }

    /***
     * 根据ID获取单位
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Units findOne(Long id){
        return unitsRepository.findOne(id);
    }

}
