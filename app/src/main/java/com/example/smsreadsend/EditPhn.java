package com.example.smsreadsend;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPhn extends AppCompatActivity {

    EditText ph;
    Button cnfrm;
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String TEXT2 = "text2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ephn);
        cnfrm = (Button) findViewById(R.id.btnCnfrm);
        ph = (EditText) findViewById(R.id.Cph);
        ph.setText(getPhn());
        cnfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhn(ph.getText().toString());
            }
        });
    }

    public void savePhn(String tempUsr) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT2, tempUsr);
        editor.apply();

        Toast.makeText(EditPhn.this, "Phone number Changed Successfully", Toast.LENGTH_LONG).show();
    }

    public String getPhn(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getString(TEXT2, "0000 000000");
    }
}