package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sachingupta.android_smarttodolist.DB.DatabaseHandler;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;
import com.sachingupta.android_smarttodolist.ToDo.ToDoCustomAdapter;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {
    ListView myToDoListView;
    Context context;
    ArrayList<ToDo> myToDOList;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        context = this;
        databaseHandler = new DatabaseHandler(context);
        myToDoListView = (ListView) findViewById(R.id.toDoListView);
        populateToDoListFromDB();
    }

    private void populateToDoListFromDB(){
        this.myToDOList = databaseHandler.getAllToDo();
        myToDoListView.setAdapter(new ToDoCustomAdapter(this, myToDOList));
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        populateToDoListFromDB();
    }
}
