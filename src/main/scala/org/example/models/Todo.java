package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"id"})
public class Todo {

    private int id;
    private int user;
    private String title;
    private boolean completed;
    private String createdAt;
    private List<Task> tasks;

    public Todo(int id, int user, String title, boolean completed, String createdAt, List<Task> tasks) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
        this.tasks = tasks;
    }

    public Todo() {
    }

    public int getId() {
        return id;
    }

    public Todo setId(int id) {
        this.id = id;
        return this;
    }

    public int getUser() {
        return user;
    }

    public Todo setUser(int user) {
        this.user = user;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Todo setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Todo setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Todo setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Todo setTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
