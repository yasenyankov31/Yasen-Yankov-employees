package org.pairfinder.models;

public class WorkingPair {
    private long firstEmployeeId;
    private long secondEmployeeId;
    private Long projectId;
    private long timeSpent;
    public WorkingPair() {
    }

    public WorkingPair(Long firstEmployeeId, Long secondEmployeeId, Long projectId, long timeSpent) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.projectId = projectId;
        this.timeSpent = timeSpent;
    }

    public Long getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(Long firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public Long getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(Long secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
