package com.zeral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "ico")
    private String ico;

    @Column(name = "url")
    private String url;

    private Integer sort;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "has_parent")
    private Boolean hasParent;

    @Column(name = "has_son")
    private Boolean hasSon;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Menu menu;

    @OneToMany(mappedBy = "menu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Menu> children = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "menu_role",
               joinColumns = @JoinColumn(name="menus_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="roles_code", referencedColumnName="code"))
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Menu title(String title) {
        this.title = title;
        return this;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIco() {
        return ico;
    }

    public Menu ico(String ico) {
        this.ico = ico;
        return this;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getUrl() {
        return url;
    }

    public Menu url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Menu getMenu() {
        return menu;
    }

    public Menu menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public Menu children(List<Menu> menus) {
        this.children = menus;
        return this;
    }

    public Menu addChildren(Menu menu) {
        this.children.add(menu);
        menu.setMenu(this);
        return this;
    }

    public Menu removeChildren(Menu menu) {
        this.children.remove(menu);
        menu.setMenu(null);
        return this;
    }

    public void setChildren(List<Menu> menus) {
        this.children = menus;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Menu roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }


    public Menu addRole(Role role) {
        this.roles.add(role);
        role.getMenus().add(this);
        return this;
    }

    public Menu removeRole(Role role) {
        this.roles.remove(role);
        role.getMenus().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
