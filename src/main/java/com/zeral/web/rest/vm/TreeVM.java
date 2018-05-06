package com.zeral.web.rest.vm;
import java.util.List;

public class TreeVM {
    private String text;
    private Long value;
    private boolean checked;
    private List<TreeVM> children;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

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

    public List<TreeVM> getChildren() {
        return children;
    }
    public void setChildren(List<TreeVM> children) {
        this.children = children;
    }
}
