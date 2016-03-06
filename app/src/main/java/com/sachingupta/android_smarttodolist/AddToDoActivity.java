package com.sachingupta.android_smarttodolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sachingupta.android_smarttodolist.DB.DatabaseHandler;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;

import java.util.Date;

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
    static final int DATE_DIALOG_ID = 999;
    private EditText selectedTime;
    private int year = 2016;
    private int month = 1;
    private int day = 1;
    ImageView toDoStartDatePickerIV;
    ImageView toDoEndDatePickerIV;

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

        toDoStartDatePickerIV = (ImageView) findViewById(R.id.toDoStartDatePicker);

        toDoStartDatePickerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = startTimeET;
                showDialog(DATE_DIALOG_ID);
            }
        });

        toDoEndDatePickerIV = (ImageView) findViewById(R.id.toDoEndDatePicker);

        toDoEndDatePickerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTime = endTimeET;
                showDialog(DATE_DIALOG_ID);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            Date date = new Date(year, month, day);
            selectedTime.setText(date.toString());
        }
    };
}
