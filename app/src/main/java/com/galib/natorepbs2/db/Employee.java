package com.galib.natorepbs2.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee_table")
public class Employee {
    @NonNull
    @ColumnInfo(name = "priority")
    int priority;
    @NonNull
    @ColumnInfo(name = "image_url")
    String imageUrl;
    @NonNull
    @ColumnInfo(name = "name")
    String name;
    @NonNull
    @ColumnInfo(name = "designation")
    String designation;
    @NonNull
    @ColumnInfo(name = "office")
    String office;

    @ColumnInfo(name = "email")
    String email;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mobile")
    String mobile;

    @ColumnInfo(name = "phone")
    String phone;

    public int getPriority() {
        return priority;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDesignation() {
        return designation;
    }

    @NonNull
    public String getOffice() {
        return office;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    public String getMobile() {
        return mobile;
    }

    public String getPhone() {
        return phone;
    }

    public int getType() {
        return type;
    }

    @NonNull
    @ColumnInfo(name = "type")
    int type;
    public Employee(int priority, String imageUrl, String name, String designation, String office, String email, String mobile, String phone, int type){
        this.priority = priority;
        this.imageUrl = imageUrl;
        this.name = name;
        this.designation =  designation;
        this.office = office;
        this.email = email;
        this.mobile = mobile;
        this.phone = phone;
        this.type = type;
    }
}
