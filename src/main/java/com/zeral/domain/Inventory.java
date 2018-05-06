package com.zeral.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by karen on 2018/1/29.
 * */
@Entity
@Table(name = "inventory")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventory {
    private long id;
    private String name;
    private Date createTime;
    private String des;
    private Boolean deleteStatue;
    private Integer statue;
    private String proId;
    private Project project;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "des")
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
    @Column(name = "statue")
    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }
    @Basic
    @Column(name = "pro_id")
    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventory that = (Inventory) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (des != null ? !des.equals(that.des) : that.des != null) return false;
        if (deleteStatue != null ? !deleteStatue.equals(that.deleteStatue) : that.deleteStatue != null) return false;
        if (statue != null ? !statue.equals(that.statue) : that.statue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (des != null ? des.hashCode() : 0);
        result = 31 * result + (deleteStatue != null ? deleteStatue.hashCode() : 0);
        result = 31 * result + (statue != null ? statue.hashCode() : 0);
        return result;
    }
}
