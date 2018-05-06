package com.zeral.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "line_process", schema = "manager")
public class LineProcess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "line_id")
    private Long lineId;
    @Column(name = "process_id")
    private Long processId;

    @Column(name = "delete_statue")
    private Boolean deleteStatue;
    @Column(name = "sort")
    private int sort;
    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LineBody lineBody;
    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Process process;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Boolean getDeleteStatue() {
        return deleteStatue;
    }

    public void setDeleteStatue(Boolean deleteStatue) {
        this.deleteStatue = deleteStatue;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public LineBody getLineBody() {
        return lineBody;
    }

    public void setLineBody(LineBody lineBody) {
        this.lineBody = lineBody;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LineProcess that = (LineProcess) o;

        if (id != that.id) return false;
        if (lineId != null ? !lineId.equals(that.lineId) : that.lineId != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (deleteStatue != null ? !deleteStatue.equals(that.deleteStatue) : that.deleteStatue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (lineId != null ? lineId.hashCode() : 0);
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (deleteStatue != null ? deleteStatue.hashCode() : 0);
        return result;
    }
}
