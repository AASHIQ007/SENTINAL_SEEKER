package com.example.smsreadsend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button rstP,eUsr,ePhn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rstP= (Button) findViewById(R.id.resetP);
        eUsr= (Button) findViewById(R.id.editUsr);
        ePhn= (Button) findViewById(R.id.editPh);

        rstP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPw();
            }
        });
        eUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUs();
            }
        });
        ePhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPh();
            }
        });

    }
    void goToPw(){
        Intent intent;
        intent=new Intent(this,RstP.class);
        startActivity(intent);
    }
    void goToUs(){
        Intent intent;
        intent=new Intent(this,EditUsr.class);
        startActivity(intent);
    }
    void goToPh(){
        Intent intent;
        intent=new Intent(this,EditPhn.class);
        startActivity(intent);
    }
}
