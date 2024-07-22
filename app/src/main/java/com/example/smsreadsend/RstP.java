package com.example.smsreadsend;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RstP extends AppCompatActivity {

    EditText op,np1,np2;
    Button cnfrm;
    public static final String SHARED_PREFS="SharedPrefs";
    public static final String TEXT="text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPwd();
        setContentView(R.layout.activity_rstp);
        cnfrm= (Button) findViewById(R.id.btnCnfrm);
        op= (EditText) findViewById(R.id.Opw);
        np1= (EditText) findViewById(R.id.NPw);
        np2= (EditText) findViewById(R.id.CPw);
        cnfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Confun();
            }
        });
    }
    public void Confun(){
        ChangePass(op.getText().toString(),np1.getText().toString(),np2.getText().toString());
    }

    public void ChangePass(String oldP,String newP1,String newP2){
        if(oldP.contains(Main_page.password))
        {
            if(newP1.contains(newP2) & newP2.contains(newP1)){
                Toast.makeText(RstP.this, "Done", Toast.LENGTH_LONG).show();
                savePwd(newP1);
            }
            else {
                Toast.makeText(RstP.this, "Confirmation Error", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(RstP.this, "Wrong Password", Toast.LENGTH_LONG).show();
        }

    }

    public void savePwd(String tempPwd){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT,tempPwd);
        editor.apply();

        Toast.makeText(RstP.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
    }

    public void getPwd(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        Main_page.password = sharedPreferences.getString(TEXT, "#1234");
    }
}
