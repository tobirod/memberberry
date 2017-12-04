package com.newton.tr.member.Models;

public class Task {

    public Task(int taskID, boolean taskStatus, boolean taskChecked, String taskDateAdded, String taskContent) {
        this.taskId = taskID;
        this.taskStatus = taskStatus;
        this.taskChecked = taskChecked;
        this.taskDateAdded = taskDateAdded;
        this.taskContent = taskContent;
    }

    private int taskId;
    private boolean taskStatus;
    private boolean taskChecked;
    private String taskDateAdded;
    private String taskContent;

    public int getTaskId() { return taskId; }
    public void setId(int taskId) { this.taskId = taskId; }

    public boolean getTaskStatus() { return taskStatus; }
    public void setTaskStatus(boolean taskStatus) { this.taskStatus = taskStatus; }

    public boolean getIsChecked() { return taskChecked; }
    public void setIsChecked(boolean taskChecked) {
        this.taskChecked = taskChecked;
    }

    public String getTaskDateAdded() { return taskDateAdded; }
    public void setTaskDateAdded(String taskDateAdded) { this.taskDateAdded = taskDateAdded; }

    public String getTaskContent() { return taskContent; }
    public void setTaskContent(String taskContent) { this.taskContent = taskContent; }

}
