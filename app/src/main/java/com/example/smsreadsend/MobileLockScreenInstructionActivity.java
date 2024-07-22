package com.example.smsreadsend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MobileLockScreenInstructionActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_lock_screen_instruction);

        txt=findViewById(R.id.textview_for_lock);
        txt.setText("MyHelper <yourLoginPassword> setScreenLock");

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Lock Mobile Screen");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Main_page.class));
            }
        });
    }
}
