package com.BielowCorp.dod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.BielowCorp.dod.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DodPlay extends AppCompatActivity implements View.OnClickListener {
   public Button btn_start,btn_drink,btn_do,btn_back;


   public TextView isPlayer,tusk;

    final String SAVED_TEXT = "save_text";
    public SharedPreferences sPref;

   public List<String> playerList = new ArrayList<>();
   public List<String> taskList = new ArrayList<>();
   public List<String> SpecialTaskList = new ArrayList<>();
   public String[] array;
   public String[] array2;
   public  InterstitialAd ad;
   public int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dod_play);
        MobileAds.initialize(this,"ca-app-pub-3447851932140136~4340625830");
        ad = new InterstitialAd(this);
        ad.setAdUnitId("ca-app-pub-3447851932140136/3946422903");
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
        ad.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                try{
                            Intent i = new Intent(DodPlay.this, DrinkOrDO.class);
                            startActivity(i);
                            finish();

                }catch(Exception e){

                }
            }
        });
        loadPlayers();
        loadTusk();
        loadSpecialTusk();



        isPlayer = findViewById(R.id.isPlayer);
        tusk = findViewById(R.id.task);

        btn_start = findViewById(R.id.btn_start);
        btn_drink = findViewById(R.id.btn_drink);
        btn_do = findViewById(R.id.btn_do);
        btn_back = findViewById(R.id.btn_back2);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_drink.setVisibility(View.VISIBLE);
                btn_do.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
                isPlayer.setText(playerList.get(i));
                int sizeTaskList = taskList.size();
                int rand = getRandIndex();
                if(rand>sizeTaskList){
                    int Index = getRandSpecialIndex();
                    tusk.setText(SpecialTaskList.get(Index));
                    SpecialTaskList.remove(Index);
                    btn_do.setClickable(true);
                    btn_drink.setClickable(true);
                    btn_do.setVisibility(View.INVISIBLE);
                }else {
                    tusk.setText(taskList.get(rand));
                    taskList.remove(rand);
                    btn_do.setClickable(true);
                    btn_drink.setClickable(true);
                }

            }
        });
        btn_back.setOnClickListener(this);

        btn_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        int length = playerList.size() - 1;
                        if(i==length){i=0;}
                        else{i++;}
                        isPlayer.setText(playerList.get(i));
                        btn_start.setVisibility(View.VISIBLE);
                        btn_start.setText(getResources().getString(R.string.ready));
                        btn_start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btn_start.setVisibility(View.INVISIBLE);
                                btn_drink.setVisibility(View.VISIBLE);
                                btn_do.setVisibility(View.VISIBLE);
                                isPlayer.setText(playerList.get(i));
                                int sizeTaskList = taskList.size();
                                int rand = getRandIndex();
                                if(rand>sizeTaskList){
                                    int Index = getRandSpecialIndex();
                                    tusk.setText(SpecialTaskList.get(Index));
                                    SpecialTaskList.remove(Index);
                                    btn_do.setClickable(true);
                                    btn_drink.setClickable(true);
                                    btn_do.setVisibility(View.INVISIBLE);
                                }else {
                                    tusk.setText(taskList.get(rand));
                                    taskList.remove(rand);
                                    btn_do.setClickable(true);
                                    btn_drink.setClickable(true);
                                }
                            }
                        });
                    }
                };
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(()->r.run(),3000);
                btn_drink.setVisibility(View.INVISIBLE);
                btn_do.setVisibility(View.INVISIBLE);
                isPlayer.setText(playerList.get(i) + " " + getResources().getString(R.string.Do_do_do));
                tusk.setText(null);
                btn_do.setClickable(false);
                btn_drink.setClickable(false);

            }
        });

        btn_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        int length = playerList.size() - 1;
                        if(i==length){i=0;}
                        else{i++;}
                        isPlayer.setText(playerList.get(i));
                        btn_start.setVisibility(View.VISIBLE);
                        btn_start.setText(getResources().getString(R.string.ready));
                        btn_start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btn_start.setVisibility(View.INVISIBLE);
                                btn_drink.setVisibility(View.VISIBLE);
                                btn_do.setVisibility(View.VISIBLE);
                                isPlayer.setText(playerList.get(i));
                                int sizeTaskList = taskList.size();
                                int rand = getRandIndex();
                                if(rand>sizeTaskList){
                                    int Index = getRandSpecialIndex();
                                    tusk.setText(SpecialTaskList.get(Index));
                                    SpecialTaskList.remove(Index);
                                    btn_do.setClickable(true);
                                    btn_drink.setClickable(true);
                                    btn_do.setVisibility(View.INVISIBLE);
                                }else {
                                    tusk.setText(taskList.get(rand));
                                    taskList.remove(rand);
                                    btn_do.setClickable(true);
                                    btn_drink.setClickable(true);
                                }
                            }
                        });
                    }
                };
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(()->r.run(),3000);
                btn_drink.setVisibility(View.INVISIBLE);
                btn_do.setVisibility(View.INVISIBLE);
                isPlayer.setText(playerList.get(i) + " " + getResources().getString(R.string.drink_drink_drink));
                tusk.setText(null);
                btn_do.setClickable(false);
                btn_drink.setClickable(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back2:
                if(ad.isLoaded()){
                    ad.show();
                }else {
                    Intent intent = new Intent(this, DrinkOrDO.class);
                    startActivity(intent);
                    finish();
                }
                break;

        }

    }

    public void loadPlayers(){
        sPref = getSharedPreferences("PlayersName",MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT,"");
        StringTokenizer st= new StringTokenizer(savedText,"\n");
        while(st.hasMoreTokens()){
            String str = st.nextToken();
            if(!str.isEmpty()){ playerList.add(str);}
        }
    }
    public void loadTusk(){
        array =  getResources().getStringArray(R.array.Task);
        for(int i=0;i<array.length;i++){
        taskList.add(array[i]);
        }
        array=null;
    }
    public void loadSpecialTusk(){
        array2 =  getResources().getStringArray(R.array.special_task);
        for(int i=0;i<array2.length;i++){
            SpecialTaskList.add(array2[i]);
        }
        array2=null;
    }
    public int getRandIndex(){
        int length = (taskList.size() - 1)+(SpecialTaskList.size() - 1);
        int result;
        if(length>0){ result = 0 + (int) (Math.random()*length);}
        else {result = 0;}
        return result;
    }
    public int getRandSpecialIndex(){
        int length = SpecialTaskList.size() - 1;
        int result;
        if(length>0){ result = 0 + (int) (Math.random()*length);}
        else {result = 0;}
        return result;
    }


}