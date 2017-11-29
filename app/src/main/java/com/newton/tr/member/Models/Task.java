package com.newton.tr.member.Models;

public class Task {

    public Task(int taskID, boolean taskStatus, String dateAdded, String task) {
        this.ID = taskID;
        this.status = taskStatus;
        this.dateAdded = dateAdded;
        this.task = task;
    }

    private int ID;
    private boolean status;
    private String dateAdded;
    private String task;


    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

}
