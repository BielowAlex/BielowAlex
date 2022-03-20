package com.BielowCorp.dod;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.BielowCorp.dod.R;
import com.BielowCorp.dod.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DrinkOrDO extends AppCompatActivity implements View.OnClickListener {

    public Dialog add,remove;
    public int id = 0;
    public ArrayList<Text> players= new ArrayList<>();
    private Button btn_play;
    final String SAVED_TEXT = "save_text";
    public SharedPreferences sPref;
    public ListAdapter adapter;

    public ActivityMainBinding binding;
    public InterstitialAd ad;
    public int terminate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main2);

        MobileAds.initialize(this,"ca-app-pub-3447851932140136~4340625830");
        ad = new InterstitialAd(this);
        ad.setAdUnitId("ca-app-pub-3447851932140136/3946422903");
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);


        ad.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                try{
                    switch (terminate) {
                        case 1:
                            Intent i = new Intent(DrinkOrDO.this, MainActivity.class);
                            startActivity(i);
                            finish();
                            break;
                        case 2:
                            Intent intent = new Intent(DrinkOrDO.this, DodPlay.class);
                            startActivity(intent);
                            finish();
                            break;
                    }
                }catch(Exception e){

                }
            }
        });


        btn_play = findViewById(R.id.btn_play);
        btn_play.setClickable(false);
        btn_play.setOnClickListener(this);
        ListView list_of_players = findViewById(R.id.lists);
        adapter = new ListAdapter(this,players);
        list_of_players.setAdapter(adapter);
        list_of_players.setClickable(true);

        loadPlayers();
        sPref = getSharedPreferences("PlayersName",MODE_PRIVATE);
        SharedPreferences.Editor ed =  sPref.edit();
        ed.clear();
        remove = new Dialog(this);
        remove.requestWindowFeature(Window.FEATURE_NO_TITLE);
        remove.setContentView(R.layout.dialog_remove);
        remove.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        remove.setCancelable(false);//нельзя закрыть кнопкой назад
        EditText enters1 = remove.findViewById(R.id.enters1);
        list_of_players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView close1= remove.findViewById(R.id.btn_close1);
                enters1.setText(players.get(position).getText());
                Button btn_rename,btn_remove;
                btn_rename = remove.findViewById(R.id.btn_ok);
                btn_remove = remove.findViewById(R.id.btn_remove);
                btn_rename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String player = String.valueOf(enters1.getText());
                        if(!player.isEmpty()) { enters1.setText(null);remove.dismiss();players.get(position).setText(player); }
                    }
                });
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        players.remove(position);
                        adapter.notifyDataSetChanged();

                        onLoadInfo();
                        remove.dismiss();
                    }
                });
                close1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { try{ remove.dismiss(); }catch(Exception e){ } }});//обработка закрытия диалогового окна
                remove.show();
            }
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        Button btn_add = findViewById(R.id.btn_add);
        add = new Dialog(this);
        add.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add.setContentView(R.layout.dialog_add);
        add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add.setCancelable(false);//нельзя закрыть кнопкой назад
        EditText enters = add.findViewById(R.id.enters2);

        btn_add.setOnClickListener(new View.OnClickListener() {  //Обработка нажатия
            @Override
            public void onClick(View v) {
                TextView close= add.findViewById(R.id.btn_close);//кнопка закрытия диалогового окна
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { try{ add.dismiss(); }catch(Exception e){ } }});//обработка закрытия диалогового окна

                Button btn_add2 = add.findViewById(R.id.btn_add2);

                btn_add2.setOnClickListener(new View.OnClickListener() {//функция добавления игрока в список
                    @Override
                    public void onClick(View v) {
                            String player = String.valueOf(enters.getText());
                            if(!player.isEmpty()) { enters.setText(null);add.hide();addPlayer(player); }
                    }
                });
                add.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    btn_back.setBackgroundTintList();
//                }
                if(ad.isLoaded()) {
                    terminate = 1;
                    ad.show();
                }else {

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_play:
                if(ad.isLoaded()) {
                    terminate = 2;
                    ad.show();
                }else {
                    Intent intent1 = new Intent(this, DodPlay.class);
                    startActivity(intent1);
                    finish();
                }
                break;
        }
    }
    //Функция добавление имени игрока в список и сохранения его имени в базе данных
    public void addPlayer(String str){
        players.add(new Text(id,str));
        adapter.notifyDataSetChanged();
       // if(id == 0){result =   str + "\n";}else{ result +=   str + "\n";}

        id++;
        if(id==2){
            btn_play.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            btn_play.setClickable(true);
        }
        onLoadInfo();

    }
    //Функция сохранения имени игрока в локальной базе
    private void savedInfo(String result) {
        sPref = getSharedPreferences("PlayersName",MODE_PRIVATE);
        SharedPreferences.Editor ed =  sPref.edit();
        ed.putString(SAVED_TEXT,result);
        ed.commit();

    }
    public void loadPlayers(){
        sPref = getSharedPreferences("PlayersName",MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT,"");
        StringTokenizer st= new StringTokenizer(savedText,"\n");
        while(st.hasMoreTokens()){
            String str = st.nextToken();

            players.add(new Text(id,str));
                adapter.notifyDataSetChanged();
                id++;
                if (id == 2) {
                    btn_play.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                    btn_play.setClickable(true);
                }
        }


       }
        public void onLoadInfo(){
            id = players.size();
            if(id < 2){btn_play.setTextColor(ColorStateList.valueOf(Color.parseColor("#4DFFFFFF")));
                btn_play.setClickable(false);}
            int s = players.size();
            String result = null;
            if(s > 1){
                for (int i=0;i<players.size();++i) {
                    if (i == 0) {
                        result = players.get(i).getText() + "\n";
                    }else {
                        result += players.get(i).getText() + "\n";}
                }
            }
            adapter.notifyDataSetChanged();
            savedInfo(result);
}


    }
