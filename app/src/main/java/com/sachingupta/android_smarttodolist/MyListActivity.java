package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sachingupta.android_smarttodolist.ToDo.ToDo;
import com.sachingupta.android_smarttodolist.ToDo.ToDoCustomAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MyListActivity extends AppCompatActivity {

    ListView myToDoListView;
    Context context;

    ArrayList<ToDo> myToDOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        context = this;
        initializeToDoList();
        myToDoListView = (ListView) findViewById(R.id.toDoListView);
        myToDoListView.setAdapter(new ToDoCustomAdapter(this, myToDOList));

    }

    private void initializeToDoList(){
        this.myToDOList = new ArrayList<ToDo>();

        this.myToDOList.add(new ToDo("T1", "D1", new Date(), new Date()));
        this.myToDOList.add(new ToDo("T2", "D2", new Date(), new Date()));
        this.myToDOList.add(new ToDo("T3", "D3", new Date(), new Date()));
    }
}
