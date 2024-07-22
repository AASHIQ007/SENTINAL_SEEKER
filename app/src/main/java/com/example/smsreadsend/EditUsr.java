package com.example.smsreadsend;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUsr extends AppCompatActivity {

    EditText us;
    Button cnfrm;
    public static final String SHARED_PREFS="SharedPrefs";
    public static final String TEXT1="text1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eusr);
        cnfrm= (Button) findViewById(R.id.btnCnfrm);
        us= (EditText) findViewById(R.id.Cus);
        us.setText(getUsr());
        cnfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsr(us.getText().toString());
            }
        });
    }

    public void saveUsr(String tempUsr){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT1,tempUsr);
        editor.apply();

        Toast.makeText(EditUsr.this, "Usename Changed Successfully", Toast.LENGTH_LONG).show();
    }

    public String getUsr(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getString(TEXT1, "Username");
    }
}