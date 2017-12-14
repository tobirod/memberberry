package com.newton.tr.member.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.newton.tr.member.BR;

public class ViewModel extends BaseObservable {

    private boolean taskDeleteMode;
    private boolean shopDeleteMode;
    private boolean noteDeleteMode;

    @Bindable
    public boolean getTaskDeleteMode() { return taskDeleteMode; }
    @Bindable
    public void setTaskDeleteMode(boolean taskDeleteMode) { this.taskDeleteMode = taskDeleteMode; notifyPropertyChanged(BR.taskDeleteMode); }

    @Bindable
    public boolean getShopDeleteMode() { return shopDeleteMode; }
    @Bindable
    public void setShopDeleteMode(boolean shopDeleteMode) { this.shopDeleteMode = shopDeleteMode; notifyPropertyChanged(BR.shopDeleteMode); }

    @Bindable
    public boolean getNoteDeleteMode() { return noteDeleteMode; }
    @Bindable
    public void setNoteDeleteMode(boolean noteDeleteMode) { this.noteDeleteMode = noteDeleteMode; notifyPropertyChanged(BR.noteDeleteMode); }

}
