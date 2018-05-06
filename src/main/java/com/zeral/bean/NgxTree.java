package com.zeral.bean;

import java.util.List;

/**
 * ngx-treeview 组件返回tree数据
 *
 * @author Zeral
 * @date 2018-03-06
 */
public class NgxTree {
    private String text;

    private Long value;

    private boolean checked;

    private List<NgxTree> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<NgxTree> getChildren() {
        return children;
    }

    public void setChildren(List<NgxTree> children) {
        this.children = children;
    }
}
