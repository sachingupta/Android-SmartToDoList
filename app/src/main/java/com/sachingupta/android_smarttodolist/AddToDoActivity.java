package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.sachingupta.android_smarttodolist.DB.DatabaseHandler;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;

public class AddToDoActivity extends AppCompatActivity {

    EditText titleET;
    EditText descriptionET;
    EditText categoryET;
    EditText startTimeET;
    EditText endTimeET;
    Button addToDoSubmitBtn;
    Context context;
    DatabaseHandler databaseHandler;
    ToDo currentToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        context = this;

        Intent intent = getIntent();
        currentToDo = (ToDo)intent.getSerializableExtra("toDoObject");

        addToDoSubmitBtn = (Button) findViewById(R.id.addToDoSubmitBtn);
        titleET = (EditText) findViewById(R.id.toDoTitle);
        titleET.setText(currentToDo.Title);
        descriptionET = (EditText) findViewById(R.id.toDoDescription);
        descriptionET.setText(currentToDo.Description);
        categoryET = (EditText) findViewById(R.id.toDoCategory);
        categoryET.setText(currentToDo.Category);

        startTimeET = (EditText) findViewById(R.id.toDoStartTime);
        startTimeET.setText(currentToDo.StartTime);
        endTimeET = (EditText) findViewById(R.id.toDoEndTime);
        endTimeET.setText(currentToDo.EndTime);
        databaseHandler = new DatabaseHandler(context);

        categoryET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddToDoCategorySelectionActivity.class);
                updateToDo();
                intent.putExtra("toDoObject", currentToDo);
                startActivity(intent);
            }
        });

        addToDoSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToDo();
                databaseHandler.addToDo(currentToDo);
                Toast.makeText(getApplicationContext(), "Submit successful", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void updateToDo(){
        if(currentToDo.Id == -1) {
            int toDoId = databaseHandler.nextId();
            currentToDo.Id = toDoId;
        }
        currentToDo.Category = categoryET.getText().toString();
        currentToDo.update(titleET.getText().toString(), descriptionET.getText().toString(), startTimeET.getText().toString(), endTimeET.getText().toString());
    }
}
