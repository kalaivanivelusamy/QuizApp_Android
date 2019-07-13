package com.example.examplenavapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

public class ScoreActivity extends AppCompatActivity {

    Button btn;
    private InterstitialAd interstitial;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btn = (Button)findViewById(R.id.button2);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showAlert();
                return false;
            }
        });

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adIRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad Activity
        interstitial = new InterstitialAd(ScoreActivity.this);

        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.interstitialAd));

        // Interstitial Ad load Request
        interstitial.loadAd(adIRequest);

        // Prepare an Interstitial Ad Listener
//        interstitial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                // Call displayInterstitial() function when the Ad loads
//                displayInterstitial();
//            }
//        });




        /*** Reward Video ****/

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

       mRewardedVideoAd.loadAd(getString(R.string.rewardAd), new AdRequest.Builder().build());
        /*getString(R.string.rewardAd*/


        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(),
                        "Ad loaded.", Toast.LENGTH_SHORT).show();
                if (mRewardedVideoAd.isLoaded()) {
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

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(),
                        Integer.toString(rewardItem.getAmount()), Toast.LENGTH_SHORT).show();
                addScore(rewardItem.getAmount());
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(getBaseContext(),
                        "Ad left application.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getBaseContext(),
                        "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });




    }


    public void displayInterstitial()
    {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
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


    // Add score method
    private void addScore(int amt)
    {
        Log.d("Score of the game",Integer.toString(amt));
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
}

