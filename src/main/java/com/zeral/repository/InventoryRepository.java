package com.zeral.repository;


import com.zeral.domain.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karen on 2018/1/29.
 * */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Page<Inventory> findAllByProIdAndStatueAndNameLikeAndDeleteStatueIsTrue(String proId, Integer statue, String name, Pageable pageable);
    Page<Inventory> findAllByProIdAndNameLikeAndDeleteStatueIsTrue(String proId, String name, Pageable pageable);
}
