package org.pairfinder.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
    private Long id;
    private Long projectId;
    private Date fromDate;
    private Date toDate;

    public Employee(long id, long projectId, Date fromDate, Date toDate) {
        this.id = id;
        this.projectId = projectId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String Info() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return "Employee id: " + id + ", Project id: " + projectId + ", From date: " + formatter.format(fromDate) + ", To date: " + formatter.format(toDate);
    }
}
