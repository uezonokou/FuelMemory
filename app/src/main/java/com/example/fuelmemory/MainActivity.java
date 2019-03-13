package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static int money;
    public TextView Recently;
    public TextView Fuelavg;
    public TextView Moneyavg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Recently=findViewById(R.id.Recently_Fuel);

        Fuelavg=findViewById(R.id.avgFuel);

        Moneyavg=findViewById(R.id.avgFuel);



        Button kirokubutton=findViewById(R.id.MemoryButton);
        kirokubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(),MemoryActivity.class);
                startActivity(intent);
            }
        });


        Button history=findViewById(R.id.historyButton);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),HistoryActivity.class);
                startActivity(intent);
            }
        });



    }
}
