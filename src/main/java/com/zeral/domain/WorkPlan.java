package com.zeral.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by karen on 2018/1/30.
 * */
@Entity
@Table(name = "work_plan")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkPlan {
    private long id;
    private String depId;
    private String proId;
    private String name;
    private String parentId;
    private Boolean hasParent;
    private Boolean hasSon;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Boolean deleteStatue;
    private Integer state;
    private Date delayTime;
    private Date actualTime;
    private Department department;
    private Project project;
    private Integer sort;
    private WorkPlan workPlan;
    private List<WorkPlan> childrenWorkPlan = new ArrayList<>();

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
    @Column(name = "dep_id")
    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    @Basic
    @Column(name = "pro_id")
    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
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
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "has_parent")
    public Boolean getHasParent() {
        return hasParent;
    }

    public void setHasParent(Boolean hasParent) {
        this.hasParent = hasParent;
    }

    @Basic
    @Column(name = "has_son")
    public Boolean getHasSon() {
        return hasSon;
    }

    public void setHasSon(Boolean hasSon) {
        this.hasSon = hasSon;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
    @Column(name = "delete_statue")
    public Boolean getDeleteStatue() {
        return deleteStatue;
    }

    public void setDeleteStatue(Boolean deleteStatue) {
        this.deleteStatue = deleteStatue;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "delay_time")
    public Date getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Date delayTime) {
        this.delayTime = delayTime;
    }

    @Basic
    @Column(name = "actual_time")
    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    @ManyToOne
    @JoinColumn(name = "dep_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    public WorkPlan getWorkPlan() {
        return workPlan;
    }

    public WorkPlan workPlan(WorkPlan workPlan) {
        this.workPlan = workPlan;
        return this;
    }
    public void setWorkPlan(WorkPlan workPlan) {
        this.workPlan = workPlan;
    }

    @OneToMany(mappedBy = "workPlan")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public List<WorkPlan> getChildrenWorkPlan() {
        return childrenWorkPlan;
    }

    public WorkPlan childrenWorkPlan(List<WorkPlan> workPlans) {
        this.childrenWorkPlan = workPlans;
        return this;
    }

    public WorkPlan addChildrenWorkPlan (WorkPlan WorkPlan) {
        this.childrenWorkPlan.add(WorkPlan);
        WorkPlan.setWorkPlan(this);
        return this;
    }

    public WorkPlan removeChildrenWorkPlan(WorkPlan WorkPlan) {
        this.childrenWorkPlan.remove(WorkPlan);
        WorkPlan.workPlan(null);
        return this;
    }
    public void setChildrenWorkPlan(List<WorkPlan> childrenWorkPlan) {
        this.childrenWorkPlan = childrenWorkPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkPlan that = (WorkPlan) o;

        if (id != that.id) return false;
        if (depId != null ? !depId.equals(that.depId) : that.depId != null) return false;
        if (proId != null ? !proId.equals(that.proId) : that.proId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (hasParent != null ? !hasParent.equals(that.hasParent) : that.hasParent != null) return false;
        if (hasSon != null ? !hasSon.equals(that.hasSon) : that.hasSon != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (deleteStatue != null ? !deleteStatue.equals(that.deleteStatue) : that.deleteStatue != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (delayTime != null ? !delayTime.equals(that.delayTime) : that.delayTime != null) return false;
        if (actualTime != null ? !actualTime.equals(that.actualTime) : that.actualTime != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (depId != null ? depId.hashCode() : 0);
        result = 31 * result + (proId != null ? proId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (hasParent != null ? hasParent.hashCode() : 0);
        result = 31 * result + (hasSon != null ? hasSon.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deleteStatue != null ? deleteStatue.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (delayTime != null ? delayTime.hashCode() : 0);
        result = 31 * result + (actualTime != null ? actualTime.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);

        return result;
    }
}
