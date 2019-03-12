package com.example.fuelmemory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_memory);

        Button sendButton = findViewById(R.id.sendMemory);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
