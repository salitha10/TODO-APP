package com.example.todo;

public class ToDoModel {

    private int id;
    private String Description, Title;
    private long started, finished;

    public ToDoModel(int id, String description, String title, long started, long finished) {
        this.id = id;
        Description = description;
        Title = title;
        this.started = started;
        this.finished = finished;
    }

    public ToDoModel(String description, String title, long started, long finished) {
        Description = description;
        Title = title;
        this.started = started;
        this.finished = finished;
    }

    public ToDoModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }
}
