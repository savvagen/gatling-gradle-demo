package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id"})
public class Task {

    private int id;
    private int todo;
    private String description;
    private boolean completed;
    private String startTime;
    private String endTime;

    public Task(int id, int todo, String description, boolean completed, String startTime, String endTime) {
        this.id = id;
        this.todo = todo;
        this.description = description;
        this.completed = completed;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public int getTodo() {
        return todo;
    }

    public Task setTodo(int todo) {
        this.todo = todo;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Task setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Task setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Task setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}
