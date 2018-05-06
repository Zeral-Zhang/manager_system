package com.zeral.service;

import com.zeral.domain.Supplier;
import com.zeral.repository.SupplierRepository;
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
public class SupplierService {

    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /***
     * 保存供应商
     * @param supplier
     * @return
     */
    public Supplier save(Supplier supplier) {
        log.debug("Request to save Supplier : {}", supplier);
        supplier.setCreateDate(new Date());
        supplier.setState(true);
        return supplierRepository.save(supplier);
    }

    /***
     * 更新供应商
     * @param id
     * @return
     */
    public Supplier update(Long id, Supplier supplier) {
        log.debug("REST request to update Supplier : {}", supplier);
        Supplier supplier1 = supplierRepository.findOne(id);
        supplier1.setName(supplier.getName());
        supplier1.setFullName(supplier.getFullName());
        supplier1.setLinkMan(supplier1.getLinkMan());
        supplier1.setContactInformation(supplier.getContactInformation());
        return supplier1;
    }

    /**
     * 删除供应商
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Supplier : {}", id);
        Supplier supplier1 = supplierRepository.findOne(id);
        supplier1.setState(false);
    }

    /***
     * 获取供应商列表(分页)
     * @param query
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Supplier> getAllSupplier(String query, Pageable pageable) {
        return supplierRepository.findAllByNameLikeAndStateIsTrue(query,pageable);
    }


    /***
     * 获取供应商列表(不分页)
     * @return
     */
    @Transactional(readOnly = true)
    public List<Supplier> getAllSupplierNoPage() {
        return supplierRepository.findAllSupplier();
    }

    /***
     * 根据ID获取供应商
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Supplier findOne(Long id){
        return supplierRepository.findOne(id);
    }

}
