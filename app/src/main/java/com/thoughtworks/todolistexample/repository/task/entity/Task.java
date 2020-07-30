package com.thoughtworks.todolistexample.repository.task.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String deadline;

    @ColumnInfo
    private boolean done;

    @ColumnInfo
    private boolean remind;

    public Task(int id, String title, String description, String deadline, boolean done, boolean remind) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.remind = remind;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isRemind() {
        return remind;
    }
}
