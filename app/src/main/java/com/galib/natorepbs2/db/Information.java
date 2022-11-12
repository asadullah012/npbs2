package com.galib.natorepbs2.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "information_table")
public class Information {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "priority")
    private int priority;

    public Information(int priority, String title, String description, String category) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.category = category;
    }


    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
