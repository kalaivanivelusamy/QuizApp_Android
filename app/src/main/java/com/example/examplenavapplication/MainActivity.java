package com.example.examplenavapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.google.android.gms.ads.MobileAds;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;


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
        categoryNames.put(13,"Entertainment:Musicals & Theatre"); //not available
        categoryNames.put(14,"Entertainment:Television");
        categoryNames.put(15,"Entertainment:Video Games");
        categoryNames.put(16,"Entertainment:Board Games");//not available
        categoryNames.put(17,"Science & Nature");
        categoryNames.put(18,"Science : Computers");
        categoryNames.put(19,"Science : Mathematics");//not available
        categoryNames.put(20,"Mythology");
        categoryNames.put(21,"Sports");
        categoryNames.put(22,"Geography");
        categoryNames.put(23,"History");
        categoryNames.put(24,"Politics");//not available
        categoryNames.put(25,"Art");//not available
        categoryNames.put(26,"Celebrities");//not available
        categoryNames.put(27,"Animals");//not available
        categoryNames.put(28,"Vehicles");//not available
        categoryNames.put(29,"Entertainment:Comics");//not available
        categoryNames.put(30,"Science : Gadgets");//not available
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
                highlightSelectedButton(button);

            }
        });

        buttonCat2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                highlightSelectedButton(buttonCat2);

            }
        });

        buttonCat3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                highlightSelectedButton(buttonCat3);
            }
        });




    /*
        Toolbar toolbar = findViewById(R.id.my_toolbar);
          toolbar.setTitle(getString(R.string.title_bar));
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        */

        showRandomCategories();


       // MobileAds.initialize(this,getString(R.string.admob_app_id));

        checkForUpdates();


    }


    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }




    private void highlightSelectedButton(final Button btn){

        btn.setBackgroundColor(Color.GREEN);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, CategoryLoadingScreen.class);
                intent.putExtra("categoryId",btn.getTag().toString());
                intent.putExtra("categoryName",btn.getText());

                startActivity(intent);
            }
        }, 500);

    }

    private void showRandomCategories(){

       // Random r = new Random();
        int i1 = getRandom();
        int i2 =getRandom();
        int i3 =getRandom();

        i1=reassignCategories(i1);
        i2=reassignCategories(i2);
        i3=reassignCategories(i3);

        button.setText(categoriesList.get(i1));
        button.setTag(new Integer(i1));
        buttonCat2.setText(categoriesList.get(i2));
        buttonCat2.setTag(new Integer(i2));
        buttonCat3.setText(categoriesList.get(i3));
        buttonCat3.setTag(new Integer(i3));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quitTheGame();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void quitTheGame(){
        this.finishAffinity();
    }


    private int reassignCategories(int id){
        Random r = new Random();

        switch (id){
            case 24-27:
                Log.d("ignored id",Integer.valueOf(id).toString());
                return 32;
            case 28-30:
                Log.d("ignored id",Integer.valueOf(id).toString());
                return 31;
            case 13:
                Log.d("ignored id",Integer.valueOf(id).toString());
                return 14;
            case 16:
                Log.d("ignored id",Integer.valueOf(id).toString());
                return 15;
            case 19:
                Log.d("ignored id",Integer.valueOf(id).toString());

                return 18;
                default:
                    return id;

        }
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
        checkForCrashes();
        Log.d("Life cycle events","In Resume");
    }

    @Override
    protected  void  onPause(){
        super.onPause();
        unregisterManagers();
        Log.d("Life cycle events","In pause");
    }

    @Override
    protected  void  onStop(){
        super.onStop();
        unregisterManagers();

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
