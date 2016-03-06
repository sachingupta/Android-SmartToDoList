package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sachingupta.android_smarttodolist.DB.DatabaseHandler;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;

public class AddToDoActivity extends AppCompatActivity {

    EditText titleET;
    EditText descriptionET;
    EditText startTimeET;
    EditText endTimeET;
    Button addToDoSubmitBtn;
    Context  context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        context = this;
        addToDoSubmitBtn = (Button) findViewById(R.id.addToDoSubmitBtn);
        titleET = (EditText) findViewById(R.id.toDoTitle);
        descriptionET = (EditText) findViewById(R.id.toDoDescription);
        startTimeET = (EditText) findViewById(R.id.toDoStartTime);
        endTimeET = (EditText) findViewById(R.id.toDoEndTime);
        final DatabaseHandler db = new DatabaseHandler(context);
        addToDoSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toDoId = db.nextId();
                ToDo toDo = new ToDo(toDoId, titleET.getText().toString(), descriptionET.getText().toString(), startTimeET.getText().toString(), endTimeET.getText().toString());
                db.addToDo(toDo);
                Toast.makeText(getApplicationContext(), "Submit successful", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }
}
