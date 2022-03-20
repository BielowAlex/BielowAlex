package com.BielowCorp.dod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.BielowCorp.dod.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent intent = new Intent(this,MainActivity.class);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
              startActivity(intent);
              finish();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(()->r.run(),3000);
    }
}