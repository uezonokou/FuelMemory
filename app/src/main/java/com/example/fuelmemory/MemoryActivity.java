package com.example.fuelmemory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MemoryActivity extends Activity {

    public EditText daytime;
    public EditText distance;
    public EditText fuel_amount;
    public EditText Money;

    public double first;

    public String fileHead ="Memory_";

    public RadioGroup kyoriG;
    public RadioGroup yushuG;
    public int kyoriId;
    public int yushuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent=getIntent();
        first=intent.getDoubleExtra(MainActivity.MEMORY_DATA,0);

        setContentView(R.layout.acivity_memory);

        daytime=findViewById(R.id.daytime_me);

        Date data=new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' kk'時'mm'分'ss'秒'");

        daytime.setText(sdf.format(data));

        distance=findViewById(R.id.distance_m);

        fuel_amount=findViewById(R.id.fuel_amount_m);

        Money=findViewById(R.id.Money_m);

        kyoriG=findViewById(R.id.km_choice);

        yushuG=findViewById(R.id.yushu_check);


        Button sendButton = findViewById(R.id.sendMemory);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kyoriId=kyoriG.getCheckedRadioButtonId();
                yushuId=yushuG.getCheckedRadioButtonId();
                String sendDaytime=daytime.getText().toString();
                String stdistance =distance.getText().toString();
                double dis;
                String stfuel = fuel_amount.getText().toString();
                double fuel_am;
                String stMoney = Money.getText().toString();
                double money_d;
                if(stdistance.isEmpty()==true || stfuel.isEmpty()==true || stMoney.isEmpty()==true || kyoriId== -1 || yushuId == -1) {
                    Toast.makeText(MemoryActivity.this, "項目すべてに入力してください。", Toast.LENGTH_LONG).show();
                } else {

                    fuel_am = Double.valueOf(stfuel);
                    dis = Double.valueOf(stdistance);
                    double ans = dis / fuel_am;
                    money_d = Double.valueOf(stMoney);
                    double L = money_d / fuel_am;

                    String toastmsg = "燃費は" + String.format("%.2f",ans) + "km/lです。\n1L当たり" + String.format("%.2f",L) + "円です。";

                    Toast.makeText(MemoryActivity.this, toastmsg, Toast.LENGTH_LONG).show();

                    intent.putExtra(MainActivity.MEMORY_DATA,ans);

                    String stans=String.format("%.2f",ans);
                    String stL = String.format("%.2f",L);

                    RadioButton kyoriButton =(RadioButton) findViewById(kyoriId);
                    RadioButton yushuButton =(RadioButton) findViewById(yushuId);

                    String setKyori = kyoriButton.getText().toString();
                    String setYushu = yushuButton.getText().toString();



                    saveMemory(fileHead,sendDaytime,stdistance,stfuel,stMoney,stans,stL,setKyori,setYushu);

                    setResult(RESULT_OK,intent);

                    finish();
                }
            }
        });

    }

    public void saveMemory(String header ,String day , String Distanse , String Fuels , String Moneys , String nempi , String LMoney , String putKyori , String putYush){
        try (FileOutputStream fileOutputStream = openFileOutput(header + day + ".txt", Context.MODE_PRIVATE);) {
            String enter ="\n";
            fileOutputStream.write(day.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(Distanse.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(Fuels.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(Moneys.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(nempi.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(LMoney.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(putKyori.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(putYush.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
