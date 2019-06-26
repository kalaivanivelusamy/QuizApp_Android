package com.example.examplenavapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button,buttonCat2,buttonCat3;

    ArrayList<String> categoriesList = new ArrayList<String>();
    HashMap<Integer,String>categoryNames = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryNames.put(9,"General Knowledge");
        categoryNames.put(10,"Entertainment:Books");
        categoryNames.put(11,"Entertainment:Film");
        categoryNames.put(12,"Entertainment:Music");
        categoryNames.put(13,"Entertainment:Musicals & Theatre");
        categoryNames.put(14,"Entertainment:Television");
        categoryNames.put(15,"Entertainment:Video Games");
        categoryNames.put(16,"Entertainment:Board Games");
        categoryNames.put(17,"Science & Nature");
        categoryNames.put(18,"Science : Computers");
        categoryNames.put(19,"Science : Mathematics");
        categoryNames.put(20,"Mythology");
        categoryNames.put(21,"Sports");
        categoryNames.put(22,"Geography");
        categoryNames.put(23,"History");
        categoryNames.put(24,"Politics");
        categoryNames.put(25,"Art");
        categoryNames.put(26,"Celebreties");
        categoryNames.put(27,"Animals");
        categoryNames.put(28,"Vehicles");
        categoryNames.put(29,"Entertainment:Comics");
        categoryNames.put(30,"Science : Gadgets");
        categoryNames.put(31,"Entertainment : Cartoon & Animations");
        categoryNames.put(32,"Entertainment : Japanese Anima & Manga");

        for (int i=0;i<31;i++){
            if(i<9){
            categoriesList.add("");
            }
            else
            {
                categoriesList.add(categoryNames.get(new Integer(i)));
                Log.d("category List",categoriesList.get(i));
            }
        }




        button = (Button) findViewById(R.id.buttonCat1);
        buttonCat2 = (Button) findViewById(R.id.buttonCat2);
        buttonCat3 = (Button) findViewById(R.id.buttonCat3);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CategoryLoadingScreen.class);
                intent.putExtra("categoryId",button.getTag().toString());
                intent.putExtra("categoryName",button.getText());

                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "Button category1 Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCat2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), buttonCat2.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,CategoryLoadingScreen.class);
                intent.putExtra("categoryId",buttonCat2.getTag().toString());
                intent.putExtra("categoryName",buttonCat2.getText());

                startActivity(intent);
            }
        });

        buttonCat3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), buttonCat3.getText()+ " Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, CategoryLoadingScreen.class);
                intent.putExtra("categoryId",buttonCat3.getTag().toString());
                intent.putExtra("categoryName",buttonCat3.getText());


                startActivity(intent);
            }
        });



        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.title_bar));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        showRandomCategories();



    }


    private void showRandomCategories(){

       // Random r = new Random();
        int i1 = getRandom();
        int i2 =getRandom();
        int i3 =getRandom();

        button.setText(categoriesList.get(i1));
        button.setTag(new Integer(i1));
        buttonCat2.setText(categoriesList.get(i2));
        buttonCat2.setTag(new Integer(i2));
        buttonCat3.setText(categoriesList.get(i3));
        buttonCat3.setTag(new Integer(i3));

    }

    private int getRandom(){
        Random r = new Random();
        return r.nextInt(31 - 9) + 9;
    }

    @Override
    protected  void  onStart(){
        super.onStart();
        Log.d("Life cycle events","In start");
    }

    @Override
    protected  void  onResume(){
        super.onResume();
        showRandomCategories();
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
