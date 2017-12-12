package com.newton.tr.member.Models;

public class Task {

    public Task(int taskID, boolean taskStatus, boolean isChecked, String dateAdded, String task) {
        this.ID = taskID;
        this.status = taskStatus;
        this.isChecked = isChecked;
        this.dateAdded = dateAdded;
        this.task = task;
    }

    private int ID;
    private boolean status;
    private boolean isChecked;
    private String dateAdded;
    private String task;

    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public boolean getIsChecked() { return isChecked; }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

}
