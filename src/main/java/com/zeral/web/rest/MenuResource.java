package com.zeral.web.rest;

import com.zeral.domain.Menu;
import com.zeral.domain.Role;
import com.zeral.domain.User;
import com.zeral.repository.UserRepository;
import com.zeral.security.SecurityUtils;
import com.zeral.service.MenuService;
import com.zeral.service.RoleService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import com.zeral.web.rest.vm.MenuVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing Menu.
 */
@RestController
@RequestMapping("/api")
public class MenuResource {

    private final Logger log = LoggerFactory.getLogger(MenuResource.class);

    private static final String ENTITY_NAME = "menu";

    private final MenuService menuService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public MenuResource(MenuService menuService,RoleService roleService,UserRepository userRepository) {
        this.menuService = menuService;
        this.roleService=roleService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /menus : Create a new menu.
     *
     * @param menu the menu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menu, or with status 400 (Bad Request) if the menu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menus")
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody Menu menu) throws URISyntaxException {
        log.debug("REST request to save Menu : {}", menu);
        if (menu.getId() != null) {
            throw new BadRequestAlertException("A new menu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Menu result = menuService.save(menu);
        return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menus : Updates an existing menu.
     *
     * @param menu the menu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menu,
     * or with status 400 (Bad Request) if the menu is not valid,
     * or with status 500 (Internal Server Error) if the menu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menus")
    public ResponseEntity<Menu> updateMenu(@Valid @RequestBody Menu menu) throws URISyntaxException {
        log.debug("REST request to update Menu : {}", menu);
        if (menu.getId() == null) {
            return createMenu(menu);
        }
        Menu result = menuService.save(menu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menus : get all the menus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> getAllMenus(Pageable pageable) {
        log.debug("REST request to get a page of Menus");
        Page<Menu> page = menuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/InRoleMenus")
    public ResponseEntity< List<MenuVM>> getAllMenusByRoles() {
        log.debug("REST request to get of Menus by roles");
        String loginName = SecurityUtils.getCurrentUser();
        User user = userRepository.findByDeletedIsFalseAndLogin(loginName);
        Set<Menu> menus = new HashSet<>();
        for(Role role:user.getRoles()){
            Role role1 = roleService.findOneByCode(role.getCode());
            menus.addAll(role1.getMenus());
        }
        List<Menu> menuList = new ArrayList<>();
        menuList.addAll(menus);
        List<MenuVM> menuVMS  = menuService.findMenusByRoles(menuList);
        return new ResponseEntity< List<MenuVM>>(menuVMS, null, HttpStatus.OK);
    }
    /**
     * GET  /menus/:id : get the "id" menu.
     *
     * @param id the id of the menu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menu, or with status 404 (Not Found)
     */
    @GetMapping("/menus/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        log.debug("REST request to get Menu : {}", id);
        Menu menu = menuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menu));
    }

    /**
     * DELETE  /menus/:id : delete the "id" menu.
     *
     * @param id the id of the menu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menus/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        log.debug("REST request to delete Menu : {}", id);
        menuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
