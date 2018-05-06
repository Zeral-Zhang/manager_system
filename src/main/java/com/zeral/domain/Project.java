package com.zeral.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by karen on 2018/1/31.
 * */
@Entity
@Table(name = "project")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project {
    private long id;
    private String name;
    private String simpleName;
    private Long proPlanId;
    private Date createTime;
    private Date startTime;
    private String des;
    private Boolean deleteStatue;
    private Integer state;
    private Float percent;
    private ProjectPlan projectPlan;
    private String planName;
    private String workPlanName;
    private String principal;
    private User user;
    private String principalView;
    private List<User> participants = new ArrayList<>();

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
    @Column(name = "simple_name")
    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Basic
    @Column(name = "pro_plan_id")
    public Long getProPlanId() {
        return proPlanId;
    }

    public void setProPlanId(Long proPlanId) {
        this.proPlanId = proPlanId;
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
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
    @Column(name = "principal")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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

    @OneToOne
    @JoinColumn(name = "pro_plan_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ProjectPlan getProjectPlan() {
        return projectPlan;
    }

    public void setProjectPlan(ProjectPlan projectPlan) {
        this.projectPlan = projectPlan;
    }

    @ManyToOne
    @JoinColumn(name = "principal", referencedColumnName = "id", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    @ManyToMany
    @JoinTable(
        name = "project_user",
        joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Transient
    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    @Transient
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    @Transient
    public String getWorkPlanName() {
        return workPlanName;
    }

    public void setWorkPlanName(String workPlanName) {
        this.workPlanName = workPlanName;
    }
    @Transient
    public String getPrincipalView() {
        return principalView;
    }

    public void setPrincipalView(String principalView) {
        this.principalView = principalView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project that = (Project) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (simpleName != null ? !simpleName.equals(that.simpleName) : that.simpleName != null) return false;
        if (proPlanId != null ? !proPlanId.equals(that.proPlanId) : that.proPlanId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (des != null ? !des.equals(that.des) : that.des != null) return false;
        if (deleteStatue != null ? !deleteStatue.equals(that.deleteStatue) : that.deleteStatue != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (simpleName != null ? simpleName.hashCode() : 0);
        result = 31 * result + (proPlanId != null ? proPlanId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (des != null ? des.hashCode() : 0);
        result = 31 * result + (deleteStatue != null ? deleteStatue.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);

        return result;
    }
}
