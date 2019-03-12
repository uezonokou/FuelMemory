package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HistoryActivity extends Activity {

    public static final String SendData = "null";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

         int sendFuel=0;
         final int sendMoney=400;


        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(SendData,sendMoney);
                finish();
            }

        });
    }

}
