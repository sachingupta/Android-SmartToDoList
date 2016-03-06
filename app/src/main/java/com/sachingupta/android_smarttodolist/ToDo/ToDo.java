package com.sachingupta.android_smarttodolist.ToDo;

import java.io.Serializable;
import java.util.Date;

public class ToDo implements Serializable {
    public int Id;
    public String Title;

    public String Category;

    public String SubCategory;

    public String Description;

    public String Location;

    public String StartTime;

    public String EndTime;

    public ToDoStatus Status;

    public ToDo(){
        Title = "";
        Description = "";
        this.Category = "";
        StartTime = "";
        EndTime = "";
        this.SubCategory = "";
        this.Location = "";
        this.Status = ToDoStatus.PENDING;
    }

    public ToDo(int id, String title, String description, String category, String startTime, String endTime){
        this.Id = id;
        Title = title;
        Description = description;
        this.Category = category;
        StartTime = startTime;
        EndTime = endTime;
        this.SubCategory = "";
        this.Location = "";
        this.Status = ToDoStatus.PENDING;

    }

    public ToDo(int id, String title, String description, Date startTime, Date endTime){
        this.Id = id;
        Title = title;
        Description = description;
        StartTime = startTime.toString();
        EndTime = endTime.toString();
        this.SubCategory = "";
        this.Location = "";
        this.Status = ToDoStatus.PENDING;

    }

    public void update(String title, String description, String startTime, String endTime){
        Title = title;
        Description = description;
        StartTime = startTime;
        EndTime = endTime;
    }
}
