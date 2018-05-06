package com.zeral.service;

import com.zeral.domain.Material;
import com.zeral.repository.MaterialRepository;
import com.zeral.repository.MaterialRepository;
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
public class MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialService.class);

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /***
     * 保存
     * @param material 材料
     * @return Material
     */
    public Material save(Material material) {
        log.debug("Request to save Material : {}", material);
        material.setState(true);
        material.setCreateTime(new Date());
        return materialRepository.save(material);
    }

    /***
     * 更新
     * @param id id
     * @return Material
     */
    public Material update(Long id, Material material) {
        log.debug("REST request to update Material : {}", material);
        Material material1 = materialRepository.findOne(id);
        material1.setName(material.getName());
        material1.setType(material.getType());
        material1.setSupplierId(material.getSupplierId());
        return material1;
    }

    /**
     * 删除
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        Material material = materialRepository.findOne(id);
        material.setState(false);
    }

    /***
     * 获取列表(分页)
     * @param query 设备名称
     * @param pageable 分页
     * @return Material
     */
    @Transactional(readOnly = true)
    public Page<Material> getAllMaterial(String type, String query, Pageable pageable) {
        Page<Material> result = null;
        if(type.equals("3")){
           result = materialRepository.findAllBySupplierStateAndNameLikeAndStateIsTrue(true,  query, pageable);
        }else{
            type='%' + type + '%';
           result = materialRepository.findAllBySupplierStateAndTypeLikeAndNameLikeAndStateIsTrue(true, type, query, pageable);
        }
        return result;
    }

    /***
     * 获取列表（根据厂商）
     * @param query 厂商ID
     * @return 分页
     */
    @Transactional(readOnly = true)
    public List<Material> getAllMaterialNoPage(String query) {
        return materialRepository.findAllBySupplierIdAndStateIsTrueOrderByCreateTime(query);
    }


    /***
     * 根据ID获取
     * @param id ID
     * @return Material
     */
    @Transactional(readOnly = true)
    public Material findOne(Long id) {
        return materialRepository.findOne(id);
    }

}
