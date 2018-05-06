package com.zeral.service;

import com.zeral.domain.Menu;
import com.zeral.domain.Role;
import com.zeral.repository.MenuRepository;
import com.zeral.repository.RoleRepository;
import com.zeral.web.rest.vm.MenuVM;
import com.zeral.web.rest.vm.RoleMenuVM;
import com.zeral.web.rest.vm.TreeVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    public RoleService(RoleRepository roleRepository, MenuRepository menuRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
    }

    /***
     * 保存
     * @param role
     * @return
     */
    public Role save(Role role) {
        log.debug("Request to save Role : {}", role);
        return roleRepository.save(role);
    }

    public Optional<Role> updateRole(Role role) {
        return Optional.of(roleRepository.findByCode(role.getCode())).map(roleDomain -> {
            roleDomain.setDescribe(role.getDescribe());
            roleDomain.setName(role.getName());
            return roleDomain;
        });
    }

    public Optional<Role> updateRoleMenus(RoleMenuVM roleMenu) {
        Optional<Role> role = roleRepository.findOneByCode(roleMenu.getCode());
        List<Menu> allMenuList = menuRepository.findAll();
        for (Menu menu : allMenuList) {
            Set<Role> roleSet = menu.getRoles();
            Set<Role> removeRole = new HashSet<>();
            for (Role role1 : roleSet) {
                if (roleMenu.getCode().equals(role1.getCode())) {
                    removeRole.add(role1);
                }
            }
            roleSet.removeAll(removeRole);
        }
        if (roleMenu.getMenuId().size() > 0) {
            List<Menu> menuList = menuRepository.findMenusByRoles(roleMenu.getMenuId());
            List<Long> ids = new ArrayList<>();
            //查出所有菜单，因为有父菜单
            for (Menu menu : menuList) {
                ids.addAll(this.createMenuIds(menu, ids));
            }
            List<Menu> newMenuList = menuRepository.findMenusByRoles(ids);
            for (Menu menu : newMenuList) {
                Set<Role> roleSet = menu.getRoles();
                roleSet.add(role.get());
            }
        }
        return role;
    }

    /***
     * 分页获取所有角色
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Role> findAll(String query, Pageable pageable) {
        log.debug("Request to get all Role");
        return roleRepository.findAllByNameLike(query, pageable);
    }


    /**
     * 获取所有角色
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        log.debug("Request to get all Role");
        return roleRepository.findAll();
    }

    public Role findOneByCode(String code) {
        log.debug("Request to get one Role:{}", code);
        return roleRepository.getByCode(code);
    }

    /***
     * 根据code获取角色
     * @param code
     * @return
     */
    @Transactional(readOnly = true)
    public Role findOne(String code) {
        log.debug("Request to get Role : {}", code);
        return roleRepository.findByCode(code);
    }


    /***
     * 根据code删除角色
     * @param code
     */
    public void delete(String code) {
        log.debug("Request to delete Role : {}", code);
        roleRepository.deleteByCode(code);
    }

    /***
     * 根据角色菜单树
     * @param code
     */
    public List<TreeVM> getRoleOfMenus(String code) {
        //全部菜单
        List<Menu> menusList = this.menuRepository.findAllMenus();
        Set<Menu> menuSet = roleRepository.getByCode(code).getMenus();
        //该角色含有的菜单
        List<Menu> menusInRolesList = new ArrayList<>();
        menusInRolesList.addAll(menuSet);
        //一级菜单
        List<Menu> menuList = new ArrayList<>();
        for (Menu menu : menusList) {
            //查找出一级菜单
            if (!menu.getHasParent()) {
                menuList.add(menu);
            }
        }
        //去除一级菜单后的菜单
        menusList.removeAll(menuList);
        //最后的菜单树
        List<TreeVM> menuVMS = new ArrayList<>();
        for (Menu menu : menuList) {
            TreeVM menuVM = this.createMenuTree(menu, menusList, menusInRolesList);
            menuVMS.add(menuVM);
        }
        return menuVMS;
    }

    private TreeVM createMenuTree(Menu menu, List<Menu> menuList, List<Menu> roleMenus) {
        TreeVM parentTree = this.menuToTreeVM(menu, roleMenus);
        List<TreeVM> treeVMList = new ArrayList<>();
        if (menu.getHasSon()) {
            for (Menu menu1 : menuList) {
                if (menu.getId() == menu1.getMenu().getId()) {
                    TreeVM treeVMChildren;
                    treeVMChildren = this.menuToTreeVM(menu1, roleMenus);
                    if (menu1.getHasSon()) {
                        TreeVM menuVM = createMenuTree(menu1, menuList, roleMenus);
                        treeVMChildren.setChildren(menuVM.getChildren());
                    }
                    treeVMList.add(treeVMChildren);
                }
            }
            parentTree.setChildren(treeVMList);
        }
        return parentTree;
    }

    private List<Long> createMenuIds(Menu menu, List<Long> ids) {
        List<Long> idList = new ArrayList<>();
        if (!ids.contains(menu.getId())) {
            idList.add(menu.getId());
            if (menu.getHasParent()) {
                if (createMenuIds(menu.getMenu(), ids) != null) {
                    idList.addAll(createMenuIds(menu.getMenu(), ids));
                }
            }
            return idList;
        }
        return null;
    }

    private TreeVM menuToTreeVM(Menu menu, List<Menu> roleMenus) {
        TreeVM treeVM = new TreeVM();
        treeVM.setText(menu.getTitle());
        treeVM.setValue(menu.getId());
        for (Menu menu1 : roleMenus) {
            if (menu.getId() == menu1.getId()) {
                treeVM.setChecked(true);
                break;
            }
        }
        return treeVM;
    }
}
