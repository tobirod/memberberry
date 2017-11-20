package com.newton.tr.member.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.newton.tr.member.BR;

public class ViewModel extends BaseObservable {

    private boolean taskDeleteMode;
    private boolean shopDeleteMode;
    private boolean noteDeleteMode;
    private String test = "true";

    @Bindable
    public boolean getTaskDeleteMode() { return taskDeleteMode; }
    @Bindable
    public void setTaskDeleteMode(boolean taskDeleteMode) { this.taskDeleteMode = taskDeleteMode; notifyPropertyChanged(BR.taskDeleteMode); Log.i("this", String.valueOf(this.taskDeleteMode)); Log.i("local", String.valueOf(taskDeleteMode));}

    @Bindable
    public boolean getShopDeleteMode() { return shopDeleteMode; }
    @Bindable
    public void setShopDeleteMode(boolean shopDeleteMode) { this.shopDeleteMode = shopDeleteMode; notifyPropertyChanged(BR.shopDeleteMode);
    }

    @Bindable
    public boolean getNoteDeleteMode() { return noteDeleteMode; }
    @Bindable
    public void setNoteDeleteMode(boolean noteDeleteMode) { this.noteDeleteMode = noteDeleteMode; notifyPropertyChanged(BR.noteDeleteMode);}

    @Bindable
    public String getTest() { return test; }
    @Bindable
    public void setTest(String test) { this.test = test; notifyPropertyChanged(BR.test);}
}
