package com.example.examplenavapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


public class CategoryLoadingScreen extends AppCompatActivity {

    EditText categoryName,title;
    ProgressBar pgBar;
    int progressStatus = 0;
    String categoryId="10";
    HashMap<String,ArrayList<String>> answers =new HashMap<String, ArrayList<String>>();
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_category_loading_screen);
        categoryName= (EditText) findViewById(R.id.editText2);
        title = (EditText)findViewById(R.id.editText);
        pgBar=(ProgressBar)findViewById(R.id.progressBar2);
        //pgBar.setIndeterminate(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryName.setText(extras.getString("categoryName"),TextView.BufferType.NORMAL);
            categoryId = extras.getString("categoryId");

        }
        showTimeProgress();

        getAllQuestions();
    }

    private void getAllQuestions() {
        //pgBar.setVisibility(View.VISIBLE);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                getJSONQuestions();
            }
        });

    }

    //To prevent default back button pressed
    @Override
    public void onBackPressed() {

    }

//    @Override
//    protected  void  onResume(){
//        super.onResume();
//        Intent intent = new Intent(CategoryLoadingScreen.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }


    private void showTimeProgress(){

        Log.d("timer",Integer.valueOf(progressStatus).toString());
        final Handler handler = new Handler();

        new Thread(new Runnable() {

            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1.5;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pgBar.setProgress(progressStatus);
                            //textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                if(progressStatus>=100){

                   // enableDisableAnswers(false);
                }
            }
        }).start();
    }

    private void getJSONQuestions(){

        String result;
        String inputLine;

        // Create URL
        try {
            // URL githubEndpoint = new URL("https://api.github.com/");
            URL qtnDB= new URL("https://opentdb.com/api.php?amount=20&category="+categoryId+"&difficulty=easy&type=multiple");

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


                   // questionsList.add(details.getString("question"));
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


        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressStatus=100;
                pgBar.setProgress(progressStatus);

                showTimeProgress();
                loadQuesitonsScreen();


            }
        });


    }

    private void loadQuesitonsScreen(){


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(CategoryLoadingScreen.this,QuestionsActivity.class);

                intent.putExtra("categoryName",categoryName.getText());
                Bundle bundle = new Bundle();
                bundle.putSerializable("qtnsList", mylist);
                bundle.putSerializable("incorrectAns",answers);
                intent.putExtras(bundle);


               // intent.putExtra("qtnsList",mylist);
                startActivity(intent);
            }
        }, 2500);

    }
}
