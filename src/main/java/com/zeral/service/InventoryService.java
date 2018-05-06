package com.zeral.service;

import com.zeral.domain.Inventory;
import com.zeral.repository.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by karen on 2018/1/29.
 * */
@Service
@Transactional
public class InventoryService {
    private final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository  = inventoryRepository;
    }
    /**
     * 获取分页数据
     * */
    @Transactional(readOnly = true)
    public Page<Inventory> getAllInventoryForPage(String query, Pageable pageable) {
        String[] querys = query.split("!");
        String name;
        if(querys.length == 3){
            name = "%"+querys[2]+"%";
        } else {
            name = "%%";
        }
        String proId = querys[0];
        if(querys[1].equals("undefined")) {
            return inventoryRepository.findAllByProIdAndNameLikeAndDeleteStatueIsTrue(proId,name,pageable);
        }
        Integer statue = Integer.parseInt(querys[1]);
        return inventoryRepository.findAllByProIdAndStatueAndNameLikeAndDeleteStatueIsTrue(proId,statue,name,pageable);
    }
}
