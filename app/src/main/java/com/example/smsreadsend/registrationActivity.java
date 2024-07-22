package com.example.smsreadsend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class registrationActivity extends AppCompatActivity {


    ProgressBar progressBar;
    ImageView logo_image;
    EditText editTextname,editTextemail,editTextpassword,editTextconfirmPassword,editTextlockPin;
    DBhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        logo_image=findViewById(R.id.logo);
        editTextname=(EditText)findViewById(R.id.input_name);
        editTextemail=(EditText)findViewById(R.id.input_email);
        editTextpassword=(EditText)findViewById(R.id.input_password);
        editTextconfirmPassword=(EditText)findViewById(R.id.input_confirm_password);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        Button sumbit_btn=(Button)findViewById(R.id.btn_submit);
        TextView textViewlogin=(TextView)findViewById(R.id.link_login);
        mydb = new DBhelper(this);
        logo_image.setFocusableInTouchMode(true);

        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextname.getText().toString();
                String email = editTextemail.getText().toString();
                String pass = editTextpassword.getText().toString();
                String conpass = editTextconfirmPassword.getText().toString();

                if (user.equals("") || email.equals("") || pass.equals("") || conpass.equals(""))
                {
                    Toast.makeText(registrationActivity.this,"complete the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (pass.equals(conpass))
                    {
                        Boolean emailcheckresult = mydb.checkemail(email);
                        if (emailcheckresult == false)
                        {
                            Boolean regresult = mydb.insertData(user,email,pass);
                            if (regresult == true)
                            {
                                Toast.makeText(registrationActivity.this,"REGISTRATION SUCCESSFUL",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(registrationActivity.this,"REGISTRATION FAILED" ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(registrationActivity.this,"User alreagy exists \n Login in ",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(registrationActivity.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }


}
