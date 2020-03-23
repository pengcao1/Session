package com.cp.rxjava.models;

import lombok.Data;

@Data
public class Task {
    private String description;
    private boolean isComplete;
    private int priority;

    public Task(String description, boolean isComplete, int priority){
        this.description = description;
        this.isComplete = isComplete;
        this.priority = priority;
    }

}
