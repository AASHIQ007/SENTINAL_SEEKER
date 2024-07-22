package com.example.smsreadsend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    public static String PREFS_NAME = "MyPrefsFile";

    EditText editTextemail,editTextpassword;
    Button login_btn;
    TextView textViewRegister,textViewForgot;
    ProgressBar progressBar;
    ImageView logo_image;
    DBhelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logo_image=findViewById(R.id.logo);
        editTextemail=(EditText)findViewById(R.id.input_email);
        editTextpassword=(EditText)findViewById(R.id.input_password);
        login_btn=(Button)findViewById(R.id.btn_login);
        textViewRegister=(TextView)findViewById(R.id.link_register);
        textViewForgot=(TextView)findViewById(R.id.link_forgotPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        mydb = new DBhelper(this);
        logo_image.setFocusableInTouchMode(true);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn",true);
                editor.commit();
                startActivity(new Intent(LoginActivity.this,Main_page.class));
                finish();
                String email = editTextemail.getText().toString();
                String pass = editTextpassword.getText().toString();

                if (email.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this,"Enter the Credentials",Toast.LENGTH_SHORT).show();
                }
                else {
                  Boolean result = mydb.checkpassword(email,pass);
                  if (result == true)
                  {
                      Intent intent = new Intent(getApplicationContext(),Main_page.class);
                      startActivity(intent);
                  }
                }
            }
        });

        textViewForgot .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),forgotPassword.class));
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registrationActivity.class));
            }
        });
    }
}
