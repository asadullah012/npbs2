package com.galib.natorepbs2.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "complain_center_table")
public class ComplainCentre {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    String name;
    @NonNull
    @ColumnInfo(name = "mobile_no")
    String mobileNo;
    @ColumnInfo(name="priority")
    int priority;
    public ComplainCentre(int priority, @NonNull String name, @NonNull String mobileNo){
        this.name = name;
        this.mobileNo = mobileNo;
        this.priority = priority;
    }
    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getMobileNo() {
        return mobileNo;
    }
}
