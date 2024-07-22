package com.example.smsreadsend;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText feedbk;
    Button btnSub;
//    @Override
    Vibrator vibrator;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ratingBar = (RatingBar) findViewById(R.id.rbRate);
        feedbk = (EditText) findViewById(R.id.feedbk);
        btnSub = (Button) findViewById(R.id.btnSub);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr(v);
            }
        });
    }

    public void vibr(View view) {
        vibrator.vibrate(50);
    }

}
