package com.example.fuelmemory;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;

public class MemoryActivity extends Activity {

    public EditText daytime;
    public EditText distance;
    public EditText fuel_amount;
    public EditText Money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_memory);

        daytime=findViewById(R.id.daytime_me);

        Date data=new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' kk'時'mm'分'ss'秒'");

        daytime.setText(sdf.format(data));

        distance=findViewById(R.id.distance_m);

        fuel_amount=findViewById(R.id.fuel_amount_m);

        Money=findViewById(R.id.Money_m);



        Button sendButton = findViewById(R.id.sendMemory);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendDaytime=daytime.getText().toString();
                String stdistance =distance.getText().toString();
                double dis = Double.valueOf(stdistance);
                String stfuel = fuel_amount.getText().toString();
                double fuel_am = Double.valueOf(stfuel);
                double ans = dis / fuel_am;
                String stMoney = Money.getText().toString();
                double money_d = Double.valueOf(stMoney);
                double L = money_d / fuel_am;

                String toastmsg= "燃費は"+ans+"km/lです。\n1L当たり"+L+"円です。";

                Toast.makeText(MemoryActivity.this,toastmsg,Toast.LENGTH_LONG).show();


                finish();
            }
        });

    }
}
