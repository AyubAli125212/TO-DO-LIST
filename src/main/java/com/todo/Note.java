package com.todo;

import java.io.Serializable;

public class Note implements Serializable {
    private String note;
    private Boolean isCompleted;
    private int id;

    public Note(String note, int id, boolean isCompleted) {
        this.note = note;
        this.isCompleted = isCompleted;
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public int getId() {
        return id;
    }
}
