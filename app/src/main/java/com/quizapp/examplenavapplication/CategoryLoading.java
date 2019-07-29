package com.quizapp.examplenavapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;

public class CategoryLoading extends AppCompatActivity {

    String categoryId="10";
    EditText categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_loading);

      // categoryName= (EditText) findViewById(R.id.categoryName);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryId = extras.getString("categoryId");
            //categoryName.setText(categoryId,TextView.BufferType.NORMAL);
        }
    }

}