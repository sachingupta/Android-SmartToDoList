package com.sachingupta.android_smarttodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sachingupta.android_smarttodolist.ToDo.ToDo;

public class AddToDoCategorySelectionActivity extends AppCompatActivity {
    Button addToDoCategoryNextBtn;
    Context context;
    ImageView categoryFoodIV;
    ImageView categoryAtmIV;
    ImageView categoryHinduTempleIV;
    ImageView categoryUniversityIV;
    ToDo currentToDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_category_selection);
        context = this;
        Intent intent = getIntent();
        currentToDo = (ToDo)intent.getSerializableExtra("toDoObject");
        categoryAtmIV = (ImageView) findViewById(R.id.categoryAtm);
        categoryAtmIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoActivity("ATM");
            }
        });

        categoryFoodIV = (ImageView) findViewById(R.id.categoryFood);
        categoryFoodIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoActivity("Food");
            }
        });

        categoryHinduTempleIV = (ImageView) findViewById(R.id.categoryHinduTemple);
        categoryHinduTempleIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoActivity("Hindu Temple");
            }
        });

        categoryUniversityIV = (ImageView) findViewById(R.id.categoryUniversity);
        categoryUniversityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoActivity("University");
            }
        });

        addToDoCategoryNextBtn = (Button) findViewById(R.id.addToDoCategoryNextBtn);
        addToDoCategoryNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoActivity("Food");
            }
        });
    }

    private void goToAddToDoActivity(String category){
        currentToDo.Category = category;
        Intent intent = new Intent(context, AddToDoActivity.class);
        intent.putExtra("toDoObject", currentToDo);
        startActivity(intent);
        finish();
    }
}
