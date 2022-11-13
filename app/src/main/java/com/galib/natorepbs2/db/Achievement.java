package com.galib.natorepbs2.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievement_table")
public class Achievement {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "last_value")
    private String lastValue;
    @NonNull
    @ColumnInfo(name = "cur_value")
    private String curValue;
    @NonNull
    @ColumnInfo(name = "total_value")
    private String totalValue;
    @NonNull
    @ColumnInfo(name = "priority")
    private int priority;
    @NonNull
    @ColumnInfo(name = "sl")
    private String serial;

    public Achievement(String serial, String title, String lastValue, String curValue, String totalValue, int priority){
        this.serial = serial;
        this.title = title;
        this.curValue = curValue;
        this.lastValue = lastValue;
        this.totalValue = totalValue;
        this.priority = priority;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getLastValue() {
        return lastValue;
    }

    @NonNull
    public String getCurValue() {
        return curValue;
    }

    @NonNull
    public String getTotalValue() {
        return totalValue;
    }

    @NonNull
    public String getSerial() {
        return serial;
    }

    public int getPriority() {
        return priority;
    }
}
