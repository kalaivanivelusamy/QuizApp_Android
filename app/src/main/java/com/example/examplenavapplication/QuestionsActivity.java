package com.example.examplenavapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.graphics.Color;
import android.os.AsyncTask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.util.JsonReader;
import android.app.AlertDialog.Builder;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import android.os.*;
import java.net.URLDecoder;
import java.util.concurrent.Delayed;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class QuestionsActivity extends AppCompatActivity {

    Button button,button2,button3,button4,tapBtn,indicator1Btn,indicator2Btn,indicator3Btn;
    TextView txtView;
    ProgressBar pgBar,timeBar;
    int nextQtn=-1,correctAnsCount=0;
    Object correctAnsBtnTag=0;
    Boolean isCorrectAnsClicked=false;
    ArrayList<String> questionsList = new ArrayList<String>();
    String categoryId="10";
    HashMap<String,ArrayList<String>> inCorrectanswers =new HashMap<String, ArrayList<String>>();
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    int progressStatus = 0,totalQtnsShown=0;
    LinearLayout layout;
    private AdView mAdView;
    AdRequest adRequest;
    String deviceId;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions);

        layout = (LinearLayout)findViewById(R.id.layout);

        button = (Button) findViewById(R.id.ansBtn1);
        button2 = (Button) findViewById(R.id.ansBtn2);
        button3 = (Button) findViewById(R.id.ansBtn3);
        button4 = (Button) findViewById(R.id.ansBtn4);

//        indicator1Btn = (Button) findViewById(R.id.indicator1);
//        indicator2Btn = (Button) findViewById(R.id.indicator2);
//        indicator3Btn = (Button) findViewById(R.id.indicator3);

        tapBtn = (Button) findViewById(R.id.buttonTap);

        txtView = (TextView) findViewById(R.id.textView);

//        Toolbar toolbar = findViewById(R.id.my_toolbar);
//        toolbar.setTitle(getString(R.string.title_bar));
//        setSupportActionBar(toolbar);

        pgBar = (ProgressBar) findViewById(R.id.progressBar);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryId = extras.getString("categoryId");
            mylist = (ArrayList<HashMap<String, String>>) getIntent().getExtras().getSerializable("qtnsList");
            inCorrectanswers=(HashMap<String,ArrayList<String>>) getIntent().getExtras().getSerializable("incorrectAns");

        }

        for (int i=0;i<inCorrectanswers.size();i++){
           ArrayList<String> wrongAns= inCorrectanswers.get("incorrect_answers"+nextQtn);
            Log.d("wrong ans list", String.valueOf(inCorrectanswers.size()));
        }


        randomQtnNumberGenerator();

       // getAllQuestions();
        ChangeNextQuestion();
        attachClickListener(tapBtn);
       // resetAllIndicators();

//

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                String correctAns= mylist.get(nextQtn).get("correct_answer").toString();
                String chosenAns = button.getText().toString();

                if(correctAns.contentEquals(chosenAns)){
                    Log.d("Correct answer","Correct");
                    button.setBackgroundColor(Color.GREEN);
                    isCorrectAnsClicked=true;
                }

                else{
                    button.setBackgroundColor(Color.RED);
                    isCorrectAnsClicked=false;
                    setCorrectAnsBtn();
                }



                enableDisableAnswers(false);
                setIndicators();
                return false;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                String correctAns= mylist.get(nextQtn).get("correct_answer").toString();
                String chosenAns = button2.getText().toString();

                if(correctAns.contentEquals(chosenAns)){
                    Log.d("Correct answer","Correct");
                    button2.setBackgroundColor(Color.GREEN);
                    isCorrectAnsClicked=true;

                }
                else{
                    button2.setBackgroundColor(Color.RED);
                    isCorrectAnsClicked=false;
                    setCorrectAnsBtn();

                }

                enableDisableAnswers(false);
                setIndicators();
                return false;
            }
        });

        button3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String correctAns= mylist.get(nextQtn).get("correct_answer").toString();
                String chosenAns = button3.getText().toString();

                if(correctAns.contentEquals(chosenAns)){
                    Log.d("Correct answer","Correct");
                    button3.setBackgroundColor(Color.GREEN);
                    isCorrectAnsClicked=true;

                }
                else{
                    button3.setBackgroundColor(Color.RED);
                    isCorrectAnsClicked=false;
                    setCorrectAnsBtn();

                }


                enableDisableAnswers(false);
                setIndicators();
                return false;
            }
        });

        button4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

              String correctAns= mylist.get(nextQtn).get("correct_answer").toString();
              String chosenAns = button4.getText().toString();

              if(correctAns.contentEquals(chosenAns)){
                  Log.d("Correct answer","Correct");
                  button4.setBackgroundColor(Color.GREEN);
                  isCorrectAnsClicked=true;
              }
              else{
                  button4.setBackgroundColor(Color.RED);
                  isCorrectAnsClicked=false;
                  setCorrectAnsBtn();
              }

                enableDisableAnswers(false);
                setIndicators();
                return false;
            }
        });


        showAd();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

       // mAdView.loadAd(adRequest);


    }

    private void randomQtnNumberGenerator(){

        Random r = new Random();
        int i1 = r.nextInt(19 - 0);
        if ((i1+3)>=mylist.size()){
            i1 = i1-3;
        }
        nextQtn = i1;
        Log.d("random question number",String.valueOf(i1));

    }


    private void showTimeProgress(){

        Log.d("timer",Integer.valueOf(progressStatus).toString());
         final Handler handler = new Handler();

        new Thread(new Runnable() {

            public void run() {
                if(progressStatus>=100){
                    Log.d("inside 100","100");
                    enableDisableAnswers(false);
                    timeOut();
                }
                while (progressStatus < 100) {
                    progressStatus += 1.5;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pgBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if(progressStatus>=100){
                    timeOut();
                    // enableDisableAnswers(false);
                }

            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionsActivity.this);
            alertDialog.setTitle("Game over");
            alertDialog.setMessage("Do you want to play again?");

            alertDialog.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                   reStartGame();
                }
            });

            alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    quitTheGame();
                }
            });

            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void quitTheGame(){

        this.finishAffinity();
    }


    private void resetAllIndicators(){

        indicator1Btn.setBackgroundColor(Color.TRANSPARENT);
        indicator2Btn.setBackgroundColor(Color.TRANSPARENT);
        indicator3Btn.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setIndicatorGreen(){
        if (nextQtn==0){
            indicator1Btn.setBackgroundColor(Color.GREEN);
        }
        else if (nextQtn==1){
            indicator2Btn.setBackgroundColor(Color.GREEN);
        }
        else if (nextQtn==2){
            indicator3Btn.setBackgroundColor(Color.GREEN);
        }

    }

    private void setIndicatorRed(){
        if (nextQtn==0){
            indicator1Btn.setBackgroundColor(Color.RED);
        }
        else if (nextQtn==1){
            indicator2Btn.setBackgroundColor(Color.RED);
        }
        else if (nextQtn==2){
            indicator3Btn.setBackgroundColor(Color.RED);
        }

    }

    private void setCorrectAnsBtn(){


        if(Integer.parseInt(correctAnsBtnTag.toString())==0){
            button.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==1){
            button2.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==2){
            button3.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==3){
            button4.setBackgroundColor(Color.GREEN);
        }


    }



    private void enableDisableAnswers(Boolean isEnabled)
    {
        button.setEnabled(isEnabled);
        button2.setEnabled(isEnabled);
        button3.setEnabled(isEnabled);
        button4.setEnabled(isEnabled);

    }



    private void ChangeNextQuestion(){


        enableDisableAnswers(true);
        layout.setAlpha(1.0f);
        tapBtn.setEnabled(true);
        nextQtn++;totalQtnsShown++;

        Random r = new Random();
        int i1 = r.nextInt(4 - 0) + 0;

        Log.d("random number",String.valueOf(i1));
        String qtn = mylist.get(nextQtn).get("question").toString();
        txtView.setText(Html.fromHtml(qtn));
       // txtView.setText(mylist.get(nextQtn).get("question"));
        ArrayList wrongAns = inCorrectanswers.get("incorrect_answers"+nextQtn);
        Log.d("Wrong ans",wrongAns.get(0).toString());

        if(i1==0){
           // button.setText(mylist.get(nextQtn).get("correct_answer").toString());
            String ans = mylist.get(nextQtn).get("correct_answer").toString();
            correctAnsBtnTag = button.getTag();

            button.setText(Html.fromHtml(ans));

            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==1){
            //button2.setText(mylist.get(nextQtn).get("correct_answer").toString());
            String ans = mylist.get(nextQtn).get("correct_answer").toString();

            correctAnsBtnTag = button2.getTag();
            button2.setText(Html.fromHtml(ans));

            button.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==2){
           // button3.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button3.getTag();
            String ans = mylist.get(nextQtn).get("correct_answer").toString();
            button3.setText(Html.fromHtml(ans));

            button2.setText(wrongAns.get(0).toString());
            button.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }
        if(i1==3){
           // button4.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button4.getTag();
            String ans = mylist.get(nextQtn).get("correct_answer").toString();
            button4.setText(Html.fromHtml(ans));

            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button.setText(wrongAns.get(2).toString());
        }

        button.setTextColor(Color.BLACK);
        button2.setTextColor(Color.BLACK);
        button3.setTextColor(Color.BLACK);
        button4.setTextColor(Color.BLACK);

        if(totalQtnsShown>2){
            //nextQtn=-1;
            tapBtn.setEnabled(false);
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
                ChangeNextQuestion();
                progressStatus=0;
                showTimeProgress();
                pgBar.setVisibility(View.VISIBLE);

                resetAllButtonsToGray();
            }
        });
    }


    private void setIndicators(){

        pgBar.setVisibility(View.GONE);

        if(isCorrectAnsClicked==true){
           // setIndicatorGreen();
            correctAnsCount++;
        }
        else{
           // setIndicatorRed();
        }

        if(totalQtnsShown>2){ //show the score screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showScoreScreen();
                }
            },1000);
        }
    }

    private void showScore(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionsActivity.this);
        alertDialog.setTitle("Score");
        alertDialog.setMessage("You have scored: "+correctAnsCount);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();

    }

//    protected  void  onResume() {
//        super.onResume();
//        reStartGame();
//    }

    private void reStartGame(){
        Intent intent = new Intent(QuestionsActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void showScoreScreen(){
        Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    private void timeOut(){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                enableDisableAnswers(false);
                layout.setAlpha(0.5f);
                tapBtn.setEnabled(true);
                showCorrectAnswer();

            }
        });

    }

    private void showCorrectAnswer(){

        if(Integer.parseInt(correctAnsBtnTag.toString())==0){
            button.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==1){
            button2.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==2){
            button3.setBackgroundColor(Color.GREEN);
        }
        if(Integer.parseInt(correctAnsBtnTag.toString())==3){
            button4.setBackgroundColor(Color.GREEN);
        }

    }


    private void getDeviceId(){

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
         deviceId = md5(android_id).toUpperCase();
        Log.i("device id=",deviceId);


    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error",e.toString());
        }
        return "";
    }

    private void  showAd() {

        mAdView = (AdView) findViewById(R.id.adView);
        getDeviceId();

        adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();
        mAdView.loadAd(adRequest);


//        for(int i=0;i<1000;i++) {
//            AdRequest adRequest = new AdRequest
//                    .Builder()
//                    .addTestDevice("BE84A62302821162F7C251AECF1AB3FC")
//                    .build();
//                mAdView.loadAd(adRequest);
//        }

    }



}
