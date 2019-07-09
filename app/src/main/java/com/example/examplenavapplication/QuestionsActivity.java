package com.example.examplenavapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        mAdView.loadAd(adRequest);


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
                    //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void quitTheGame(){

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
       // homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
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



//    private void getAllQuestions() {
//        //pgBar.setVisibility(View.VISIBLE);
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//               getJSONQuestions();
//            }
//        });
//
//    }

    private void enableDisableAnswers(Boolean isEnabled)
    {
        button.setEnabled(isEnabled);
        button2.setEnabled(isEnabled);
        button3.setEnabled(isEnabled);
        button4.setEnabled(isEnabled);

    }

//    private void getJSONQuestions(){
//
//        String result;
//        String inputLine;
//
//        // Create URL
//        try {
//           // URL githubEndpoint = new URL("https://api.github.com/");
//           URL qtnDB= new URL("https://opentdb.com/api.php?amount=10&category="+categoryId+"&difficulty=easy&type=multiple");
//
//            // Create connection
//            HttpsURLConnection myConnection =
//                    (HttpsURLConnection) qtnDB.openConnection();
//            if (myConnection.getResponseCode() == 200) {
//
//                InputStreamReader streamReader = new
//                        InputStreamReader(myConnection.getInputStream());
//
//                //Create a new buffered reader and String Builder
//                BufferedReader reader = new BufferedReader(streamReader);
//                StringBuilder stringBuilder = new StringBuilder();
//
//                //Check if the line we are reading is not null
//                while((inputLine = reader.readLine()) != null){
//                    stringBuilder.append(inputLine);
//                }
//                //Close our InputStream and Buffered reader
//                reader.close();
//                streamReader.close();
//                //Set our result equal to our stringBuilder
//                result = stringBuilder.toString();
//                JSONObject obj = new JSONObject(result);
//                JSONArray arrayQtns = obj.getJSONArray("results");
//                int ik=0;
//                while (ik<arrayQtns.length()){
//
//                    JSONObject details = arrayQtns.getJSONObject(ik);
//
//
//                     questionsList.add(details.getString("question"));
//                     ArrayList<String> incorrectArr = new ArrayList<String>();
//                     JSONArray arr = details.getJSONArray("incorrect_answers");
//                    if (arr != null) {
//                        for (int i=0;i<arr.length();i++){
//                            incorrectArr.add(arr.getString(i));
//                        }
//                    }
//
//                    inCorrectanswers.put("incorrect_answers"+ik,incorrectArr);
//                    HashMap<String,String> qtnAndAnswer = new HashMap<String, String>();
//
//                    qtnAndAnswer.put("question",details.getString("question"));
//                    qtnAndAnswer.put("correct_answer",details.getString("correct_answer"));
//                    mylist.add(qtnAndAnswer);
//
//                    ik++;
//                }
//
//            }
//
//            for (int i=0;i<mylist.size();i++){
//                Log.d("list",mylist.get(i).get("correct_answer"));
//            }
//        }
//        catch(Exception e){
//
//        }
//
//
//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                progressStatus=0;
//                showTimeProgress();
//
//                ChangeNextQuestion();
//
//            }
//        });
//
//
//    }

    private void ChangeNextQuestion(){


        enableDisableAnswers(true);
        layout.setAlpha(1.0f);
        tapBtn.setEnabled(true);
        nextQtn++;totalQtnsShown++;

        Random r = new Random();
        int i1 = r.nextInt(4 - 0) + 0;

        Log.d("random number",String.valueOf(i1));
        txtView.setText(mylist.get(nextQtn).get("question"));
        ArrayList wrongAns = inCorrectanswers.get("incorrect_answers"+nextQtn);
        Log.d("Wrong ans",wrongAns.get(0).toString());

        if(i1==0){
            button.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button.getTag();


            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==1){
            button2.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button2.getTag();

            button.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }

        if(i1==2){
            button3.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button3.getTag();

            button2.setText(wrongAns.get(0).toString());
            button.setText(wrongAns.get(1).toString());
            button4.setText(wrongAns.get(2).toString());
        }
        if(i1==3){
            button4.setText(mylist.get(nextQtn).get("correct_answer").toString());
            correctAnsBtnTag = button4.getTag();

            button2.setText(wrongAns.get(0).toString());
            button3.setText(wrongAns.get(1).toString());
            button.setText(wrongAns.get(2).toString());
        }

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

        if(totalQtnsShown>2){
            showScore();
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

    private void  showAd() {

        mAdView = (AdView) findViewById(R.id.adView);



        adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("BE84A62302821162F7C251AECF1AB3FC")
                .build();


//        for(int i=0;i<1000;i++) {
//            AdRequest adRequest = new AdRequest
//                    .Builder()
//                    .addTestDevice("BE84A62302821162F7C251AECF1AB3FC")
//                    .build();
//                mAdView.loadAd(adRequest);
//        }

    }



}
