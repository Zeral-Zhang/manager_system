package com.zeral.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 项目类型
 * Created by karen on 2018/1/25.
 * */
@Entity
@Table(name = "project_type")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectType {
    private long id;
    private String projectTypeName;
    private Boolean deleteStatue;
    private String des;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "project_type_name")
    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    @Basic
    @Column(name = "delete_statue")
    public Boolean getDeleteStatue() {
        return deleteStatue;
    }

    public void setDeleteStatue(Boolean deleteStatue) {
        this.deleteStatue = deleteStatue;
    }

    @Basic
    @Column(name = "des")
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectType that = (ProjectType) o;

        if (id != that.id) return false;
        if (projectTypeName != null ? !projectTypeName.equals(that.projectTypeName) : that.projectTypeName != null)
            return false;
        if (deleteStatue != null ? !deleteStatue.equals(that.deleteStatue) : that.deleteStatue != null) return false;
        if (des != null ? !des.equals(that.des) : that.des != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (projectTypeName != null ? projectTypeName.hashCode() : 0);
        result = 31 * result + (deleteStatue != null ? deleteStatue.hashCode() : 0);
        result = 31 * result + (des != null ? des.hashCode() : 0);
        return result;
    }
}
