package com.app.android.file_management.database;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoTask  {

    private int id;
    private String title;
    private String desp;
    private String time;
    private String date;
    private String category;
    private int isComplete;
    private String path;

    public TodoTask() {
    }

    public TodoTask(int id, String title, String desp, String time, String date, String category, int isComplete, String path) {
        this.id = id;
        this.title = title;
        this.desp = desp;
        this.time = time;
        this.date = date;
        this.category = category;
        this.isComplete = isComplete;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
