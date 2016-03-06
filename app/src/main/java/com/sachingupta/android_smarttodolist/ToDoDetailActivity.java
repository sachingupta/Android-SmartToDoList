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
