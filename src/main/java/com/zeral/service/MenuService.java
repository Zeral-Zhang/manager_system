package com.zeral.service;

import com.zeral.domain.Menu;
import com.zeral.domain.Role;
import com.zeral.repository.MenuRepository;
import com.zeral.security.SecurityUtils;
import com.zeral.web.rest.vm.MenuVM;
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
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Save a menu.
     *
     * @param menu the entity to save
     * @return the persisted entity
     */
    public Menu save(Menu menu) {
        log.debug("Request to save Menu : {}", menu);
        return menuRepository.save(menu);
    }

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Menu> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        return menuRepository.findAll(pageable);
    }

    /**
     * 查询用户所有菜单
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Menu> findUserMenu() {
        return menuRepository.findUserMenu(SecurityUtils.getCurrentUser());
    }

    /**
     * Get one menu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Menu findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findOneWithEagerRelationships(id);
    }
    @Transactional(readOnly = true)
    public List<MenuVM> findMenusByRoles(List<Menu> menus) {
        List<Menu> menuslst = new ArrayList<>();
        if(menus.size()>0){
            List<Long> ids = new ArrayList<>();
            for(int i=0;i<menus.size();i++){
               ids.add(menus.get(i).getId());
            }
            menuslst =  menuRepository.findMenusByRoles(ids);
        }

        List<Menu> menuList = new ArrayList<>();
        for(Menu menu:menuslst) {
           //查找出一级菜单
           if(!menu.getHasParent()) {
               menuList.add(menu);
           }
       }
       menuslst.removeAll(menuList);
       List<MenuVM> menuVMS = new ArrayList<>();
       for(Menu menu:menuList) {
           MenuVM menuVM = this.createMenuVMTree(menu,menuslst);
           menuVMS.add(menuVM);
       }
        return menuVMS;
    }
    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findById(id);
    }
    /**
     * Delete the menu by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.delete(id);
    }
    private MenuVM createMenuVMTree(Menu menu,List<Menu> menuList) {
        MenuVM menuVMParent = this.menuToMenuvm(menu);
        List<MenuVM> menuVMSet = new ArrayList<>();
        if(menu.getHasSon()) {
            for(Menu menu1:menuList) {
                if(menu.getId()==menu1.getMenu().getId()) {
                    MenuVM menuVMChildren;
                    menuVMChildren=this.menuToMenuvm(menu1);
                    if(menu1.getHasSon()) {
                        MenuVM menuVM = createMenuVMTree(menu1,menuList);
                        menuVMChildren.setChildren(menuVM.getChildren());
                    }
                    menuVMSet.add(menuVMChildren);
                }
            }
            menuVMParent.setChildren(menuVMSet);
        }
        return menuVMParent;
    }
    private MenuVM menuToMenuvm(Menu menu) {
        MenuVM menuVM = new MenuVM();
        menuVM.setId(menu.getId());
        menuVM.setName(menu.getTitle());
        menuVM.setIcon(menu.getIco());
        menuVM.setUrl(menu.getUrl());
        menuVM.setHasParent(menu.getHasParent());
        menuVM.setHasSon(menu.getHasSon());
        return menuVM;
    }
}
