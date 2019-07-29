package com.quizapp.examplenavapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.reward.RewardedVideoAd;

public class ScoreActivity extends AppCompatActivity {

    Button btn;
    TextView scoreText;
    private InterstitialAd interstitial;
    private RewardedVideoAd mRewardedVideoAd;
    AdRequest adIRequest;
   // String correctAnswerCount;
    ViewDialog viewdialog;
    int scored=0;

    SharedPreferences pref;

    Button indicator1Btn,indicator2Btn,indicator3Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


       // disableQthIndicators();

        viewdialog = new ViewDialog(this);

        btn = (Button)findViewById(R.id.button3);
        scoreText  =(TextView)findViewById(R.id.editText3);
        scoreText.setEnabled(false);
        btn.setVisibility(View.INVISIBLE);
        scoreText.setVisibility(View.INVISIBLE);

       // showCustomLoadingDialog();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        pref = getApplicationContext().getSharedPreferences("TotalScore",Context.MODE_PRIVATE);

        QuestionsActivity adManager = QuestionsActivity.getInstance();
        mRewardedVideoAd=adManager.getAd();

        Bundle extras = getIntent().getExtras();
        scored = getIntent().getIntExtra("score",0);

        scored = scored + pref.getInt("total",0);
        Log.d("msg",Integer.toString(scored));





       // showInterstitialAds();

        /*

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        adIRequest = new AdRequest.Builder().build();

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

       mRewardedVideoAd.loadAd(getString(R.string.rewardAd), new AdRequest.Builder().build());

        */



        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(),
                        "Ad loaded.", Toast.LENGTH_SHORT).show();

                if (mRewardedVideoAd.isLoaded()) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    mRewardedVideoAd.show();
                }

            }

            @Override
            public void onRewardedVideoAdOpened() {
                Toast.makeText(getBaseContext(),
                        "Ad opened.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(getBaseContext(),
                        "Ad started.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Toast.makeText(getBaseContext(),
                        "Ad closed.", Toast.LENGTH_SHORT).show();
                //scored = scored + 1+pref.getInt("total",0);;

                showScoreUI();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(),
                        Integer.toString(rewardItem.getAmount()), Toast.LENGTH_SHORT).show();
                scored = scored + rewardItem.getAmount();
               // showScoreUI();
                //runOnMainThread();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(getBaseContext(),
                        "Ad left application.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getBaseContext(),
                        "Ad failed to load."+ Integer.valueOf(i).toString(), Toast.LENGTH_SHORT).show();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                showScoreUI();
            }

            @Override
            public void onRewardedVideoCompleted() {

                Toast.makeText(getBaseContext(),
                        "Ad completed.", Toast.LENGTH_SHORT).show();
                //scored = scored + 1+pref.getInt("total",0);;

               // showScoreUI();
                //runOnMainThread();

            }


        });




    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quitTheGame();
        }
        return super.onKeyDown(keyCode, event);

    }



    private void showScoreUI(){

        btn.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);

        Log.d("fetch from pref score",Integer.valueOf(pref.getInt("total",0)).toString());

        storeTotalScore();
        scoreText.setText("Total score: \t "+ Integer.toString(scored));

                btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                reStartGame();
                return false;
            }
        });
    }

    private void  showCustomLoadingDialog(){

        viewdialog.showDialog();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewdialog.hideDialog();
            }
        }, 7000);
    }


    public void displayInterstitial()
    {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void showInterstitialAds(){

        // Prepare the Interstitial Ad Activity
        interstitial = new InterstitialAd(ScoreActivity.this);

        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.interstitialAd));

        // Interstitial Ad load Request
        interstitial.loadAd(adIRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function when the Ad loads
                displayInterstitial();
            }
        });

    }


    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();

    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();

    }




    private void showAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScoreActivity.this);
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
    }

    private void quitTheGame(){

        this.finishAffinity();
    }

    private void reStartGame(){

        Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void storeTotalScore(){

        pref = getApplicationContext().getSharedPreferences("TotalScore",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("total",scored);
        editor.apply();

        Log.d("shared pred game",Integer.valueOf(pref.getInt("total",0)).toString());


    }
}

