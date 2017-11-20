package com.newton.tr.member.Models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import io.realm.RealmObject;

public class Task extends RealmObject {

    @Retention(RetentionPolicy.SOURCE)
        @IntDef({TASKPRIOHIGH, TASKPRIOMEDIUM, TASKPRIOLOW})

    @interface TASKPRIO {}
    private static final int TASKPRIOHIGH = 0;
    private static final int TASKPRIOMEDIUM = 1;
    private static final int TASKPRIOLOW = 2;

    private long ID;
    private String UUID;
    private boolean status;
    private int prioLevel;
    private Date dateAdded;
    private String task;


    public long getId() { return ID; }
    public void setId(long ID) { this.ID = ID; }

    public String getUUID() { return UUID; }
    public void setUUID(String UUID) { this.UUID = UUID; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public int getPrioLevel() { return prioLevel; }
    public void setPrioLevel(int prioLevel) { this.prioLevel = prioLevel; }

    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

}