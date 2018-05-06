package com.zeral.repository;

import com.zeral.domain.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    /***
     * 根据父ID获取工序
     * @param parentId
     * @return
     */
    List<Process> findByParentIdAndDeleteStatusIsFalse(Long parentId);

    /***
     * 工序列表（不分页）
     * @return
     */
    List<Process> findAllByDeleteStatusIsFalse();
    List<Process> findAllByDeleteStatusIsFalseAndNameLike(String name);

    /***
     * 根据父ID查询工序分页
     * @param parentId
     * @param pageable
     * @return
     */
    Page<Process> findAllByParentIdAndDeleteStatusIsFalseOrderByCreateTime(Long parentId, Pageable pageable);


    /***
     * 工序分页
     * @param name
     * @param pageable
     * @return
     */
    Page<Process> findAllByNameLikeAndDeleteStatusIsFalseOrderByParentId(String name, Pageable pageable);


    /***
     * 根据名称查询工序
     * @param name
     * @return
     */
    Process findByNameAndDeleteStatusIsFalse(String name);
}
