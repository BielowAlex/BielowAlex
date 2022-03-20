package com.BielowCorp.dod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.BielowCorp.dod.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 public Button btn_back,drink_or_do;
private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-3447851932140136~4340625830");
        adView = findViewById(R.id.adView);


        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView.loadAd(adRequest1);


        btn_back =  findViewById(R.id.btn_back);

        drink_or_do = findViewById(R.id.play_drink_or_do);
        drink_or_do.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.play_drink_or_do:

                    try {
                        Intent intent = new Intent(this, DrinkOrDO.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) { }

                break;

        }
    }
}