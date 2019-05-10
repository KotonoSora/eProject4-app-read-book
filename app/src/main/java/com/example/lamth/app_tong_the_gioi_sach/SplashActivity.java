package com.example.lamth.app_tong_the_gioi_sach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_full;

public class SplashActivity extends AppCompatActivity {

    int myProgress;
    ProgressBar progressBar2;

    private int SPLASH_TIMER = 2000;
    InterstitialAd mInterstitialAd;

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(banner_full);
        requestNewInterstitial();


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //showInterstitial();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Intent streamPlayerHome = new Intent(SplashActivity.this,MainActivity_Home.class);
                startActivity(streamPlayerHome);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new ShowCustomProgressBarAsyncTask().execute();
                showInterstitial();


            }
        }, SPLASH_TIMER);

    }

    public class ShowCustomProgressBarAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPostExecute(Void result) {
            progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        }

        @Override
        protected void onPreExecute() {
            myProgress = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while(myProgress<100){
                myProgress++;
                publishProgress(myProgress);
                SystemClock.sleep(110);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded() && mInterstitialAd != null) {
            mInterstitialAd.show();
        }else{
            Intent streamPlayerHome = new Intent(SplashActivity.this,MainActivity_Home.class);
            startActivity(streamPlayerHome);
        }
    }

    ///ads
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
