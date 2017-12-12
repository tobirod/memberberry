package com.newton.tr.member.models;

public class Item {

    public Item(int itemID, boolean itemStatus, boolean isChecked, String itemName) {
        this.ID = itemID;
        this.status = itemStatus;
        this.isChecked = isChecked;
        this.name = itemName;
    }

    private int ID;
    private boolean status;
    private boolean isChecked;
    private String name;

    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public boolean getIsChecked() { return isChecked; }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}

// Possible functions in the future:

// How many? ex: 3, three
// ... of what? ex: pieces, liters, kilograms
// Categories? ex: Fruits/Veggies
// Crossed-off items goes to the bottom of the list in separate category?
