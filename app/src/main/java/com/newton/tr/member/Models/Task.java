package com.newton.tr.member.Models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Task {

    public Task(int taskID, boolean taskStatus, int prioLevel, String dateAdded, String task) {
        this.ID = taskID;
        this.status = taskStatus;
        this.prioLevel = prioLevel;
        this.dateAdded = dateAdded;
        this.task = task;
    }

    @Retention(RetentionPolicy.SOURCE)
        @IntDef({TASKPRIOHIGH, TASKPRIOMEDIUM, TASKPRIOLOW})

    @interface TASKPRIO {}
    private static final int TASKPRIOHIGH = 0;
    private static final int TASKPRIOMEDIUM = 1;
    private static final int TASKPRIOLOW = 2;

    private int ID;
    private boolean status;
    private int prioLevel;
    private String dateAdded;
    private String task;


    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public int getPrioLevel() { return prioLevel; }
    public void setPrioLevel(int prioLevel) { this.prioLevel = prioLevel; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

}
