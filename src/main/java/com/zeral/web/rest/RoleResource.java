package com.zeral.web.rest;

import com.zeral.domain.Menu;
import com.zeral.domain.Role;
import com.zeral.repository.MenuRepository;
import com.zeral.repository.RoleRepository;
import com.zeral.service.RoleService;
import com.zeral.web.rest.errors.BadRequestAlertException;
import com.zeral.web.rest.util.HeaderUtil;
import com.zeral.web.rest.util.PaginationUtil;
import com.zeral.web.rest.vm.RoleMenuVM;
import com.zeral.web.rest.vm.TreeVM;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Menu.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "role";
    private static final String ENTITY_CODE = "role";
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public RoleResource(RoleService roleService,
                        RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    /***
     *  POST  /roles 创建角色
     * @param role
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to save Role : {}", role);
        if (role.getCode() == null) {
            throw new BadRequestAlertException("A new role can't have no code", ENTITY_NAME, "idexists");
        }
        if(roleRepository.findOneByCodeIgnoreCase(role.getCode()).isPresent()) {
            throw new BadRequestAlertException("角色编号已被使用！", ENTITY_NAME, "codeExists");
        }
        if(roleRepository.findOneByNameIgnoreCase(role.getName()).isPresent()) {
            throw new BadRequestAlertException("角色名称已被使用", ENTITY_CODE, "codeExists");
        }
        Role result = roleService.save(role);
        return ResponseEntity.created(new URI("/api/roles/" + result.getCode()))
            .headers(HeaderUtil.createAlert("新增角色成功！", result.getCode()))
            .body(result);
    }

    /***
     * 更新角色
     * @param role
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to update Role : {}", role);
        if (role.getCode() == null) {
            return createRole(role);
        }
        Optional<Role> existingRole1 = roleRepository.findOneByNameIgnoreCase(role.getName());
        if(existingRole1.isPresent() && existingRole1.get().getName().equals(role.getName())) {
            throw new BadRequestAlertException("角色名称已被使用", ENTITY_NAME, "nameExists");
        }
        Optional<Role> newRole = roleService.updateRole(role);
        return ResponseUtil.wrapOrNotFound(newRole,
            HeaderUtil.createAlert("角色更新成功", role.getCode()));
    }

    /***
     * 分页获取角色列表
     *
     * @param pageable
     * @return
     */
    @GetMapping("/roles-pager")
    public ResponseEntity<List<Role>> getAllRoles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to get a page of Roles");
        String name = '%' + query + '%';
        final Page<Role> page = roleService.findAll(name, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /***
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAll() {
        log.debug("获取角色列表");
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /***
     *  GET  /roles/:code :根据code获取角色
     * @param code
     * @return
     */
    @GetMapping("/roles/{code}")
    public ResponseEntity<Role> getMenu(@PathVariable String code) {
        log.debug("REST request to get Role : {}", code);
        Role role = roleService.findOneByCode(code);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(role));
    }

    /***
     * DELETE  /roles/:code :据code删除角色
     * @param code
     * @return
     */
    @DeleteMapping("/roles/{code}")
    public ResponseEntity<Void> deleteRole(@PathVariable String code) {
        log.debug("REST request to delete Role : {}", code);
        roleService.delete(code);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "删除用户成功", code)).build();
    }
    @PutMapping("/roleMenus")
    public ResponseEntity<Role> updateRoleMenus(@RequestBody RoleMenuVM roleMenu) {
        Optional<Role> role = roleService.updateRoleMenus(roleMenu);
        return ResponseUtil.wrapOrNotFound(role,
            HeaderUtil.createAlert("更新成功", roleMenu.getCode()));
    }
    /***
     * 获取角色菜单树
     *
     * @return
     */
    @GetMapping("/roleOfMenus/{code}")
    public ResponseEntity<List<TreeVM>> getRoleOfMenus(@PathVariable String code) {
        List<TreeVM> menusList = this.roleService.getRoleOfMenus(code);
        return new ResponseEntity<>(menusList, HttpStatus.OK);
    }
}
