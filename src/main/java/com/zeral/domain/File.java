package com.zeral.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文件实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Table(name = "file", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "file_name")
    private String fileName;

    @Basic
    @Column(name = "file_path")
    private String filePath;

    @Basic
    @Column(name = "file_type")
    private String fileType;

    @Basic
    @Column(name = "des")
    private String des;

    @Basic
    @Column(name = "state")
    private Integer state;

    @Basic
    @Column(name = "upload_user_id")
    private Long uploadUserId;

    @ManyToOne
    @JoinColumn(name = "upload_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User uploadUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public User getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        this.uploadUser = uploadUser;
    }
}
