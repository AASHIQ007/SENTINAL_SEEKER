package com.example.smsreadsend;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AvatarSelect extends AppCompatActivity {

    ImageView avat,avat1,avat2,avat3,avat4,avat5,avat6,avat7,avat8,avat9;
    public static final String SHARED_PREFS="SharedPrefs";
    public static final String TEXT3="text3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        avat = (ImageView) findViewById(R.id.avat);
        avat1 = (ImageView) findViewById(R.id.avat1);
        avat2 = (ImageView) findViewById(R.id.avat2);
        avat3 = (ImageView) findViewById(R.id.avat3);
        avat4 = (ImageView) findViewById(R.id.avat4);
        avat5 = (ImageView) findViewById(R.id.avat5);
        avat6 = (ImageView) findViewById(R.id.avat6);
        avat7 = (ImageView) findViewById(R.id.avat7);
        avat8 = (ImageView) findViewById(R.id.avat8);
        avat9 = (ImageView) findViewById(R.id.avat9);
        dpchange();

        avat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar1);
                savedp("1");
            }
        });

        avat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar2);
                savedp("2");
            }
        });

        avat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar3);
                savedp("3");
            }
        });

        avat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar4);
                savedp("4");
            }
        });

        avat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar5);
                savedp("5");
            }
        });

        avat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar6);
                savedp("6");
            }
        });

        avat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar7);
                savedp("7");
            }
        });

        avat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar8);
                savedp("8");
            }
        });

        avat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avat.setImageResource(R.drawable.avatar9);
                savedp("9");
            }
        });

    }
    public void dpchange(){
        switch(getdp()){
            case "1": avat.setImageResource(R.drawable.avatar1);break;
            case "2": avat.setImageResource(R.drawable.avatar2);break;
            case "3": avat.setImageResource(R.drawable.avatar3);break;
            case "4": avat.setImageResource(R.drawable.avatar4);break;
            case "5": avat.setImageResource(R.drawable.avatar5);break;
            case "6": avat.setImageResource(R.drawable.avatar6);break;
            case "7": avat.setImageResource(R.drawable.avatar7);break;
            case "8": avat.setImageResource(R.drawable.avatar8);break;
            case "9": avat.setImageResource(R.drawable.avatar9);break;
        }
    }

    public void savedp(String temp){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT3,temp);
        editor.apply();
    }

    public String getdp(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getString(TEXT3, "1");
    }
}