package com.zeral.web.rest.vm;
import java.util.List;
import java.util.*;

public class MenuVM {
    private Long id;
    private String name;
    private String icon;
    private String url;
    private Boolean hasParent;
    private Boolean hasSon;
    private List<MenuVM> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuVM> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVM> children) {
        this.children = children;
    }

    public Boolean getHasParent() {
        return hasParent;
    }

    public void setHasParent(Boolean hasParent) {
        this.hasParent = hasParent;
    }

    public Boolean getHasSon() {
        return hasSon;
    }

    public void setHasSon(Boolean hasSon) {
        this.hasSon = hasSon;
    }
}
