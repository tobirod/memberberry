package com.newton.tr.member.models;

public class Note {

    public Note(int noteID, boolean fullView, boolean isChecked, String noteTitle, String noteBody) {
        this.ID = noteID;
        this.fullView = fullView;
        this.isChecked = isChecked;
        this.title = noteTitle;
        this.body = noteBody;
    }

    private int ID;
    private boolean fullView;
    private boolean isChecked;
    private String title;
    private String body;

    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }

    public boolean getFullView() { return fullView; }
    public void setFullView(boolean fullView) { this.fullView = fullView; }

    public boolean getIsChecked() { return isChecked; }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

}


