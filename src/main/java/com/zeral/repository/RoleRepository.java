package com.zeral.repository;

import com.zeral.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by quhy on 2017/12/28.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /***
     * 根据code查询
     * @param code
     * @return
     */
    Role findByCode(String code);
    Optional<Role> findOneByCode(String code);

    /***
     * 根据code删除
     * @param code
     * @return
     */
    Integer deleteByCode(String code);
    @EntityGraph(attributePaths = "menus")
    Role getByCode(String code);
    Optional<Role> findOneByCodeIgnoreCase(String code);
    Optional<Role> findOneByNameIgnoreCase(String name);
    Page<Role> findAllByNameLike(String query,Pageable pageable);
}

