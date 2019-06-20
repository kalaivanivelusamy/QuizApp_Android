package com.example.examplenavapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.graphics.Color;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {

    Button button,button2,button3,button4,tapBtn,indicator1Btn,indicator2Btn,indicator3Btn;
    TextView txtView;
    int nextQtn=0;
    ArrayList<String> questionsList = new ArrayList<String>();

    HashMap<String,ArrayList<String>> answers =new HashMap<String, ArrayList<String>>();
   // HashMap<String,String> qtnAndAnswer = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions);

        button = (Button) findViewById(R.id.ansBtn1);
        button2 = (Button) findViewById(R.id.ansBtn2);
        button3 = (Button) findViewById(R.id.ansBtn3);
        button4 = (Button) findViewById(R.id.ansBtn4);

        indicator1Btn = (Button) findViewById(R.id.indicator1);
        indicator2Btn = (Button) findViewById(R.id.indicator2);
        indicator3Btn = (Button) findViewById(R.id.indicator3);

        tapBtn = (Button) findViewById(R.id.buttonTap);

        txtView = (TextView) findViewById(R.id.textView);

        getAllQuestions();

//        attachClickListener(button);
//        attachClickListener(button2);
//        attachClickListener(button3);
        attachClickListener(tapBtn);




        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    button.setBackgroundColor(Color.GRAY);

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    button.setBackgroundColor(Color.GREEN);
                    button2.setBackgroundColor(Color.GRAY);
                    button3.setBackgroundColor(Color.GRAY);
                    button4.setBackgroundColor(Color.GRAY);

                }
                return false;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    button2.setBackgroundColor(Color.GRAY);

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    button2.setBackgroundColor(Color.GREEN);
                    button.setBackgroundColor(Color.GRAY);
                    button3.setBackgroundColor(Color.GRAY);
                    button4.setBackgroundColor(Color.GRAY);
                }
                return false;
            }
        });

        button3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    button3.setBackgroundColor(Color.GRAY);

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    button3.setBackgroundColor(Color.GREEN);
                    button2.setBackgroundColor(Color.GRAY);
                    button.setBackgroundColor(Color.GRAY);
                    button4.setBackgroundColor(Color.GRAY);
                }
                return false;
            }
        });

        button4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    button4.setBackgroundColor(Color.GRAY);

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    button4.setBackgroundColor(Color.GREEN);
                    button2.setBackgroundColor(Color.GRAY);
                    button3.setBackgroundColor(Color.GRAY);
                    button.setBackgroundColor(Color.GRAY);
                }
                return false;
            }
        });




    }



    private void getAllQuestions() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
               getJSONQuestions();
            }
        });



    }

    private void getJSONQuestions(){

        String result;
        String inputLine;

        // Create URL
        try {
           // URL githubEndpoint = new URL("https://api.github.com/");
           URL qtnDB= new URL("https://opentdb.com/api.php?amount=10&category=10&difficulty=easy&type=multiple");

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) qtnDB.openConnection();
            if (myConnection.getResponseCode() == 200) {

                InputStreamReader streamReader = new
                        InputStreamReader(myConnection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                JSONObject obj = new JSONObject(result);
                JSONArray arrayQtns = obj.getJSONArray("results");
                int ik=0;
                while (ik<arrayQtns.length()){

                    JSONObject details = arrayQtns.getJSONObject(ik);


                     questionsList.add(details.getString("question"));
                     ArrayList<String> incorrectArr = new ArrayList<String>();
                     JSONArray arr = details.getJSONArray("incorrect_answers");
                    if (arr != null) {
                        for (int i=0;i<arr.length();i++){
                            incorrectArr.add(arr.getString(i));
                        }
                    }

                    answers.put("incorrect_answers"+ik,incorrectArr);
                    HashMap<String,String> qtnAndAnswer = new HashMap<String, String>();

                    qtnAndAnswer.put("question",details.getString("question"));
                    qtnAndAnswer.put("correct_answer",details.getString("correct_answer"));
                    mylist.add(qtnAndAnswer);



                    ik++;
                }

            }

            for (int i=0;i<mylist.size();i++){
                Log.d("list",mylist.get(i).get("correct_answer"));
            }
        }
        catch(Exception e){

        }


    }

    private void ChangeNextQuestion(){
        nextQtn++;

        Random r = new Random();
        int i1 = r.nextInt(4 - 0) + 0;

        Log.d("random number",String.valueOf(i1));
        txtView.setText(mylist.get(nextQtn).get("question"));

        if(i1==0){
            button.setText(mylist.get(nextQtn).get("correct_answer").toString());
            ArrayList wrongAns = answers.get("incorrect_answers"+nextQtn);
            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==1){
            button2.setText(mylist.get(nextQtn).get("correct_answer").toString());
            ArrayList wrongAns = answers.get("incorrect_answers"+nextQtn);
            button.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==2){
            button3.setText(mylist.get(nextQtn).get("correct_answer").toString());
            ArrayList wrongAns = answers.get("incorrect_answers"+nextQtn);
            button2.setText(wrongAns.get(0).toString());
            button.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }
        if(i1==3){
            button4.setText(mylist.get(nextQtn).get("correct_answer").toString());
            ArrayList wrongAns = answers.get("incorrect_answers"+nextQtn);
            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button.setText(wrongAns.get(2).toString());
        }

//        switch (i1){
//            case 0:
//                button.setText(mylist.get(nextQtn).get("correct_answer"));
//                break;
//            case 1:
//                button2.setText(mylist.get(nextQtn).get("correct_answer"));
//                break;
//
//        }

        //resetAllButtonsToGray();

        if(nextQtn>=mylist.size()){
            nextQtn=0;
        }
    }

    private void resetAllButtonsToGray(){
        button4.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.GRAY);
        button3.setBackgroundColor(Color.GRAY);
        button.setBackgroundColor(Color.GRAY);
    }

    private void attachClickListener(final Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), btn.getText(), Toast.LENGTH_SHORT).show();
                ChangeNextQuestion();
                indicator1Btn.setBackgroundColor(Color.GREEN);
                indicator2Btn.setBackgroundColor(Color.RED);
                indicator3Btn.setBackgroundColor(Color.RED);
                resetAllButtonsToGray();
            }
        });
    }


}
