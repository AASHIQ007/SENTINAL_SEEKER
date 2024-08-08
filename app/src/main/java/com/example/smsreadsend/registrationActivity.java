package com.example.smsreadsend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
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
    EditText editTextname, editTextemail, editTextpassword, editTextconfirmPassword;
    TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    DBhelper mydb;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-z])" +       // at least 1 lowercase letter
                    "(?=.*[A-Z])" +       // at least 1 uppercase letter
                    "(?=.*\\d)" +         // at least 1 digit
                    "(?=.[@$!%?&])" +   // at least 1 special character
                    ".{6,}" +             // at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        logo_image = findViewById(R.id.logo);
        editTextname = findViewById(R.id.input_name);
        editTextemail = findViewById(R.id.input_email);
        editTextpassword = findViewById(R.id.input_password);
        editTextconfirmPassword = findViewById(R.id.input_confirm_password);


        progressBar = findViewById(R.id.progressbar);
        Button sumbit_btn = findViewById(R.id.btn_submit);
        TextView textViewlogin = findViewById(R.id.link_login);
        mydb = new DBhelper(this);

        sumbit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextname.getText().toString().trim();
                String email = editTextemail.getText().toString().trim();
                String pass = editTextpassword.getText().toString().trim();
                String  conpass = editTextconfirmPassword.getText().toString().trim();



                boolean valid = true;

                if (user.isEmpty()) {
                    Toast.makeText(registrationActivity.this, "User name can't be empty", Toast.LENGTH_SHORT).show();
                }

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(registrationActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }

                if (pass.isEmpty() || !PASSWORD_PATTERN.matcher(pass).matches()) {

                    Toast.makeText(registrationActivity.this, "Password should contain aleast one Loewrcase, Uppercase and Special chatacter and more than 6 character", Toast.LENGTH_SHORT).show();
                }

                if (!pass.equals(conpass)) {
                    Toast.makeText(registrationActivity.this, "Password and Confirm Password is not same", Toast.LENGTH_SHORT).show();
                }

                if (valid) {
                    // If all validations pass
                    Boolean emailcheckresult = mydb.checkemail(email);
                    if (!emailcheckresult) {
                        Boolean regresult = mydb.insertData(user, email, pass);
                        if (regresult) {
                            Toast.makeText(registrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(registrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(registrationActivity.this, "User already exists. Please log in.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}