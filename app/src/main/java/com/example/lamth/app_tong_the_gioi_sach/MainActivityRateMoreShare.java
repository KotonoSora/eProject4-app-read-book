package com.example.lamth.app_tong_the_gioi_sach;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import static com.example.lamth.app_tong_the_gioi_sach.Url_config.Url_more;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.Url_rate;
import static com.example.lamth.app_tong_the_gioi_sach.Url_config.banner_footer;

public class MainActivityRateMoreShare extends AppCompatActivity {

    Button btn_rate , btn_exit , btn_more , btn_share ;

    private AdView av;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rate_more_share);

        av = new AdView(this);
        av.setAdSize(AdSize.BANNER);
        av.setAdUnitId(banner_footer);
        AdRequest adRequest = new AdRequest.Builder().build();
        av.loadAd(adRequest);

        av.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.adView);
                if (layout != null) {
                    layout.removeAllViews();
                    layout.addView(av);
                }
            }
        });


        btn_rate = (Button) findViewById(R.id.btn_rate);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_more = (Button) findViewById(R.id.btn_more);
        btn_share = (Button) findViewById(R.id.btn_share);

        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url_rate)));
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url_more)));
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }

    @Override
    public void onPause() {
        if (av != null) {
            av.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (av != null) {
            av.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (av != null) {
            av.destroy();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
        super.onBackPressed();
    }


}
