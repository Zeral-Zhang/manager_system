package com.zeral.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 产品类型表
 * Created by karen on 2018/1/25.
* */
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "type_standard")
    private String typeStandard;
    @Column(name = "type_specification")
    private String typeSpecification;
    @Column(name = "supplier_id")
    private String supplierId;
    @Column(name = "delete_statue")
    private boolean deleteStatue;
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeStandard() {
        return typeStandard;
    }

    public void setTypeStandard(String typeStandard) {
        this.typeStandard = typeStandard;
    }

    public String getTypeSpecification() {
        return typeSpecification;
    }

    public void setTypeSpecification(String typeSpecification) {
        this.typeSpecification = typeSpecification;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isDeleteStatue() {
        return deleteStatue;
    }

    public void setDeleteStatue(boolean deleteStatue) {
        this.deleteStatue = deleteStatue;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
