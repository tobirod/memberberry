package com.newton.tr.member.Models;

public class Item {

    public Item(int taskID, boolean taskStatus, boolean isChecked, String dateAdded, String task) {
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

}







// Functions:
//
// Name
// How many? ex: 3, three
// ... of what? ex: pieces, liters, kilograms
// Categories? ex: Fruits/Veggies
// Cross-off selected item from list
// Crossed-off items goes to the bottom of the list in separate category
