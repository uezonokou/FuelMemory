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

    public static final String MEMORY_DATA="Memory of Data";

    public static int money;
    public TextView Recently;
    public TextView Fuelavg;
    public TextView Moneyavg;

    public double Favg;
    public double nenpi;
    public double Mavg;

    static final int request=1000;

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
                intent.putExtra("MEMORY_DATA",Favg);
                intent.putExtra("MEMORY_DATA",Mavg);
                startActivityForResult(intent,request);
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

    protected void onActivityResult(int requestcode, int resultcode,Intent intent){
        super.onActivityResult(requestcode,resultcode,intent);
        if(resultcode == RESULT_OK && requestcode == request && null != intent) {
            nenpi = intent.getDoubleExtra(MainActivity.MEMORY_DATA,0);
            String nenpiView=String.valueOf(String.format("%.2f",nenpi));
            Recently.setText(nenpiView+"km/l");
        }
    }
}
