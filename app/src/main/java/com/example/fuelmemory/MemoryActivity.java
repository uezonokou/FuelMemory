package com.example.fuelmemory;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MemoryActivity extends Activity {

    public EditText daytime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_memory);

        daytime=findViewById(R.id.daytime_me);

        Date data=new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' kk'時'mm'分'ss'秒'");

        daytime.setText(sdf.format(data));

        Button sendButton = findViewById(R.id.sendMemory);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendDaytime=daytime.getText().toString();
                finish();
            }
        });

    }
}
