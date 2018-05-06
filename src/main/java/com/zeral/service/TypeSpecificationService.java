package com.zeral.service;

import com.zeral.domain.TypeSpecification;
import com.zeral.repository.TypeSpecificationRepository;
import com.zeral.repository.TypeSpecificationRepository;
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
public class TypeSpecificationService {

    private final Logger log = LoggerFactory.getLogger(TypeSpecificationService.class);

    private final TypeSpecificationRepository typeSpecificationRepository;

    public TypeSpecificationService(TypeSpecificationRepository typeSpecificationRepository) {
        this.typeSpecificationRepository = typeSpecificationRepository;
    }

    /***
     * 保存
     * @param typeSpecification typeSpecification
     * @return TypeSpecification
     */
    public TypeSpecification save(TypeSpecification typeSpecification) {
        log.debug("Request to save TypeSpecification : {}", typeSpecification);
        typeSpecification.setCreateTime(new Date());
        return typeSpecificationRepository.save(typeSpecification);
    }

    /***
     * 更新
     * @param id id
     * @return TypeSpecification
     */
    public TypeSpecification update(Long id, TypeSpecification typeSpecification) {
        log.debug("REST request to update TypeSpecification : {}", typeSpecification);
        TypeSpecification typeSpecification1 = typeSpecificationRepository.findOne(id);
        if (typeSpecification.getContent() != null) {
            typeSpecification1.setContent(typeSpecification.getContent());
        }
        typeSpecification1.setEquipmentId(typeSpecification.getEquipmentId());
        return typeSpecification1;
    }


    /***
     * 更新设备类型
     * @param id
     * @param purchaseId
     * @return
     */
    public TypeSpecification update(Long id, String purchaseId) {
        TypeSpecification typeSpecification1 = typeSpecificationRepository.findOne(id);
        typeSpecification1.setEquipmentId(purchaseId);
        return typeSpecification1;
    }


    /**
     * 删除
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeSpecification : {}", id);
        typeSpecificationRepository.delete(id);
    }

    /***
     * 获取列表(分页)
     * @param query 设备名称
     * @param pageable 分页
     * @return TypeSpecification
     */
    @Transactional(readOnly = true)
    public Page<TypeSpecification> getAllTypeSpecification(String query, Pageable pageable) {
        return typeSpecificationRepository.findAllByContentLike(query, pageable);
    }

    /***
     * 获取列表（根据设备）
     * @param query 厂商ID
     * @return 分页
     */
    @Transactional(readOnly = true)
    public List<TypeSpecification> getAllTypeSpecificationNoPage(String query) {
        return typeSpecificationRepository.findAllByEquipmentIdOrderByCreateTime(query);
    }


    /***
     * 获取列表（设备选择类型）
     * @param query 厂商ID
     * @return 分页
     */
    @Transactional(readOnly = true)
    public List<TypeSpecification> getAllTypeBySelect(String query) {
        List<TypeSpecification> typeSpecifications1 = typeSpecificationRepository.findAllByEquipmentIdOrderByCreateTime(query);
        List<TypeSpecification> typeSpecifications2 = typeSpecificationRepository.findAllByEquipmentIdIsNullOrEquipmentIdOrderByCreateTime(query);
        for (TypeSpecification type2 : typeSpecifications2) {
            for (TypeSpecification type1 : typeSpecifications1) {
                if (type2.equals(type1)) {
                    type2.setChecked(true);
                } else {
                    type2.setChecked(false);
                }
            }
        }
        return typeSpecifications2;
    }


    /***
     * 根据ID获取
     * @param id ID
     * @return TypeSpecification
     */
    @Transactional(readOnly = true)
    public TypeSpecification findOne(Long id) {
        return typeSpecificationRepository.findOne(id);
    }

}
