package com.zeral.repository;

import com.zeral.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Menu entity.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("select distinct menu from Menu menu left join fetch menu.roles")
    List<Menu> findAllWithEagerRelationships();

    @Query("select menu from Menu menu left join fetch menu.roles where menu.id =:id")
    Menu findOneWithEagerRelationships(@Param("id") Long id);

    Menu findById(Long id);
    @Query(value="SELECT *FROM menu WHERE id IN(:ids) ORDER BY sort",
        nativeQuery = true)
    List<Menu> findMenusByRoles(@Param("ids") List<Long> ids);

    @Query(value = "SELECT menu.id, menu.title, menu.ico, menu.url, menu.sort, menu.type, menu.func, menu.is_available, menu.parent_id FROM menu INNER JOIN menu_role mr " +
        "ON menus_id = menu.id INNER JOIN role " +
        "ON mr.roles_code = role.code INNER JOIN user_role ur " +
        "ON ur.role_code = mr.roles_code INNER JOIN user " +
        "ON ur.user_id = user.id WHERE user.login = :login ORDER BY menu.sort ASC", nativeQuery = true)
    List<Menu> findUserMenu(@Param("login") String login);

    @Query(value="SELECT *FROM menu ORDER BY sort",
        nativeQuery = true)
    List<Menu> findAllMenus();
}
