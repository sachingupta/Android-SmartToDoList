package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sachingupta.android_smarttodolist.DB.DatabaseHandler;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;

public class ToDoDetailActivity extends AppCompatActivity {
    EditText titleET;
    EditText descriptionET;
    EditText startTimeET;
    EditText endTimeET;
    Button toDoDetailEditBtn;
    Button toDoDetailSaveBtn;
    Context context;
    ToDo toDo;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);
        context = this;
        Intent intent = getIntent();
        final ToDo toDo = (ToDo)intent.getSerializableExtra("toDoObject");
        titleET = (EditText) findViewById(R.id.toDoDetailTitle);
        descriptionET = (EditText) findViewById(R.id.toDoDetailDescription);
        startTimeET = (EditText) findViewById(R.id.toDoDetailStartTime);
        endTimeET = (EditText) findViewById(R.id.toDoDetailEndTime);
        initialize(toDo);
        toDoDetailEditBtn = (Button) findViewById(R.id.toDoDetailEditBtn);
        toDoDetailSaveBtn = (Button) findViewById(R.id.toDoDetailSaveBtn);
        databaseHandler = new DatabaseHandler(context);
        toDoDetailEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditMode();
                toDoDetailEditBtn.setVisibility(View.GONE);
                toDoDetailSaveBtn.setVisibility(View.VISIBLE);
            }
        });

        toDoDetailSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDo.update(titleET.getText().toString(), descriptionET.getText().toString(), startTimeET.getText().toString(), endTimeET.getText().toString());
                databaseHandler.updateToDo(toDo);
                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    public void initialize(ToDo toDo){
        titleET.setText(toDo.Title);
        titleET.setEnabled(false);

        descriptionET.setText(toDo.Description);
        descriptionET.setEnabled(false);

        startTimeET.setText(toDo.StartTime.toString());
        startTimeET.setEnabled(false);

        endTimeET.setText(toDo.EndTime.toString());
        endTimeET.setEnabled(false);
    }

    public void enableEditMode(){
        titleET.setEnabled(true);
        descriptionET.setEnabled(true);
        startTimeET.setEnabled(true);
        endTimeET.setEnabled(true);
    }
}
