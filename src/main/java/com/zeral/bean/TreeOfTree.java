package com.zeral.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public class TreeOfTree {
    public String name;
    public Long id;
    public List<TreeOfTree> children;

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

    public List<TreeOfTree> getChildren() {
        return children;
    }

    public void setChildren(List<TreeOfTree> children) {
        this.children = children;
    }
}

