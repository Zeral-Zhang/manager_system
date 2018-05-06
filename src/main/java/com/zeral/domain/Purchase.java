package com.zeral.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Created by quhy on 2018/1/24.
 */
@Entity
@Table(name = "purchase")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "type", nullable = true, length = 50)
    private String type;

    @Column(name = "create_time", nullable = true)
    private Date createTime;

    @Column(name = "state")
    private Boolean state;


    @Column(name = "supplier_id", nullable = true, length = 50)
    private String supplierId;


    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (id != purchase.id) return false;
        if (name != null ? !name.equals(purchase.name) : purchase.name != null) return false;
        if (type != null ? !type.equals(purchase.type) : purchase.type != null) return false;
        if (createTime != null ? !createTime.equals(purchase.createTime) : purchase.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
