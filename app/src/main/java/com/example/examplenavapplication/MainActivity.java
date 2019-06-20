package com.example.examplenavapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    Button button,buttonCat2,buttonCat3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Life cycle events", "onCreate: ");
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonCat1);
        buttonCat2 = (Button) findViewById(R.id.buttonCat2);
        buttonCat3 = (Button) findViewById(R.id.buttonCat3);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuestionsActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Button category1 Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCat2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), buttonCat2.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,QuestionsActivity.class);
                startActivity(intent);
            }
        });

        buttonCat3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), buttonCat3.getText()+ " Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });



        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.title_bar));
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected  void  onStart(){
        super.onStart();
        Log.d("Life cycle events","In start");
    }

    @Override
    protected  void  onResume(){
        super.onResume();
        Log.d("Life cycle events","In Resume");
    }

    @Override
    protected  void  onPause(){
        super.onPause();
        Log.d("Life cycle events","In pause");
    }

    @Override
    protected  void  onStop(){
        super.onStop();
        Log.d("Life cycle events","In stop");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
