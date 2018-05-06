package com.zeral.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by quhy on 2018/3/9.
 */
@Entity
@Table(name = "process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Process implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @Column(name = "create_time", nullable = true)
    private Date createTime;

    @Column(name = "delete_status", nullable = true)
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Process parentProcess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Process getParentProcess() {
        return parentProcess;
    }

    public void setParentProcess(Process parentProcess) {
        this.parentProcess = parentProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Process process = (Process) o;

        if (id != null ? !id.equals(process.id) : process.id != null) return false;
        if (name != null ? !name.equals(process.name) : process.name != null) return false;
        if (parentId != null ? !parentId.equals(process.parentId) : process.parentId != null) return false;
        if (createTime != null ? !createTime.equals(process.createTime) : process.createTime != null) return false;
        if (deleteStatus != null ? !deleteStatus.equals(process.deleteStatus) : process.deleteStatus != null)
            return false;
        return parentProcess != null ? parentProcess.equals(process.parentProcess) : process.parentProcess == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deleteStatus != null ? deleteStatus.hashCode() : 0);
        result = 31 * result + (parentProcess != null ? parentProcess.hashCode() : 0);
        return result;
    }
}
