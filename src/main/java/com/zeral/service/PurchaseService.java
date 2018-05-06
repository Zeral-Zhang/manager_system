package com.zeral.service;

import com.zeral.domain.Purchase;
import com.zeral.repository.PurchaseRepository;
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
public class PurchaseService {

    private final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    /***
     * 保存
     * @param purchase 外购件
     * @return Purchase
     */
    public Purchase save(Purchase purchase) {
        log.debug("Request to save Purchase : {}", purchase);
        purchase.setState(true);
        purchase.setCreateTime(new Date());
        return purchaseRepository.save(purchase);
    }

    /***
     * 更新
     * @param id id
     * @return Purchase
     */
    public Purchase update(Long id, Purchase purchase) {
        log.debug("REST request to update Purchase : {}", purchase);
        Purchase purchase1 = purchaseRepository.findOne(id);
        purchase1.setName(purchase.getName());
        purchase1.setType(purchase.getType());
        purchase1.setSupplierId(purchase.getSupplierId());
        return purchase1;
    }

    /**
     * 删除
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Purchase : {}", id);
        Purchase purchase = purchaseRepository.findOne(id);
        purchase.setState(false);
    }

    /***
     * 获取列表(分页)
     * @param query 设备名称
     * @param pageable 分页
     * @return Purchase
     */
    @Transactional(readOnly = true)
    public Page<Purchase> getAllPurchase(String query, Pageable pageable) {
        return purchaseRepository.findAllBySupplierStateAndNameLikeAndStateIsTrue(true, query, pageable);
    }

    /***
     * 获取列表（根据厂商）
     * @param query 厂商ID
     * @return 分页
     */
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchaseNoPage(String query) {
        return purchaseRepository.findAllBySupplierIdAndStateIsTrueOrderByCreateTime(query);
    }


    /***
     * 根据ID获取
     * @param id ID
     * @return Purchase
     */
    @Transactional(readOnly = true)
    public Purchase findOne(Long id) {
        return purchaseRepository.findOne(id);
    }

}
