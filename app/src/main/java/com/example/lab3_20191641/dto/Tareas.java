package com.example.lab3_20191641.dto;

public class Tareas {

    private int id;
    private String todo;
    private boolean completed;
    private int userId;

    public Tareas(int id, String todo, boolean completed, int userId) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
