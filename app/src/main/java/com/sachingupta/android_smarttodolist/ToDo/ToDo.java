package com.sachingupta.android_smarttodolist.ToDo;

import java.io.Serializable;
import java.util.Date;

public class ToDo implements Serializable {
    public String Title;

    public String Category;

    public String SubCategory;

    public String Description;

    public String Location;

    public Date StartTime;

    public Date EndTime;

    public ToDoStatus Status;

    public ToDo(String title, String description, Date startTime, Date endTime){
        Title = title;
        Description = description;
        StartTime = startTime;
        EndTime = endTime;

    }
}
