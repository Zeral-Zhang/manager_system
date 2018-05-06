package com.zeral.web.rest.vm;

import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */
public class RoleMenuVM {
    private String code;
    private List<Long> menuId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Long> getMenuId() {
        return menuId;
    }






    public void setMenuId(List<Long> menuId) {
        this.menuId = menuId;
    }
}
