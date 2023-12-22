package com.todo.app.DTO;

public class TodoUser {
    private String name;
    private String discription;
    private long userId;

    public TodoUser(String name, String discription, long userId) {
        this.name = name;
        this.discription = discription;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
