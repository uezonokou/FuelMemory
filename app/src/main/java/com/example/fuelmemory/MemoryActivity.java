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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MemoryActivity extends Activity {

    public EditText daytime;
    public EditText distance;
    public EditText fuel_amount;
    public EditText Money;

    public String fileHead ="Memory_";
    public String Filename="ODO.txt";
    public String allFuel="AllFuel.txt";
    public String allMoney ="AllMoney.txt";

    public String ODO;
    public String push;

    public RadioGroup kyoriG;
    public RadioGroup yushuG;
    public int kyoriId;
    public int yushuId;

    public double distance_all;
    public double ans;
    public double dis;
    public String setKyori;

    public boolean discheck;
    public boolean Fuelcheck;
    public boolean Moneycheck;


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

        kyoriG=findViewById(R.id.km_choice);

        yushuG=findViewById(R.id.yushu_check);

        ODO=readODO(Filename);

        distance_all=Double.valueOf(ODO);


        Button sendButton = findViewById(R.id.sendMemory);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kyoriId=kyoriG.getCheckedRadioButtonId();
                yushuId=yushuG.getCheckedRadioButtonId();
                String sendDaytime=daytime.getText().toString();
                String stdistance =distance.getText().toString();
                discheck=numcheck(stdistance);
                String stfuel = fuel_amount.getText().toString();
                Fuelcheck=numcheck(stfuel);
                double fuel_am;
                String stMoney = Money.getText().toString();
                Moneycheck=numcheck(stMoney);
                double money_d;

                if(discheck==false || Fuelcheck==false || Moneycheck==false){
                    Toast.makeText(MemoryActivity.this,"数値で入力してください。",Toast.LENGTH_LONG).show();
                } else if(stdistance.isEmpty()==true || stfuel.isEmpty()==true || stMoney.isEmpty()==true || kyoriId== -1 || yushuId == -1) {
                    Toast.makeText(MemoryActivity.this, "項目すべてに入力してください。", Toast.LENGTH_LONG).show();
                } else {

                    fuel_am = Double.valueOf(stfuel);
                    dis = Double.valueOf(stdistance);


                    money_d = Double.valueOf(stMoney);
                    double L = money_d / fuel_am;

                    RadioButton kyoriButton = (RadioButton) findViewById(kyoriId);
                    RadioButton yushuButton = (RadioButton) findViewById(yushuId);

                    setKyori = kyoriButton.getText().toString();
                    String setYushu = yushuButton.getText().toString();

                    double TRIP_value;


                    if (setKyori.equals("ODO")) {
                        TRIP_value = dis - distance_all;
                        ans = TRIP_value / fuel_am;
                        distance_all = dis;
                        stdistance = String.valueOf(TRIP_value);

                    } else if (setKyori.equals("TRIP")) {
                        distance_all = distance_all + dis;
                        ans = dis / fuel_am;
                    }

                    String toastmsg = "燃費は" + String.format("%.2f", ans) + "km/lです。\n1L当たり" + String.format("%.2f", L) + "円です。";

                    Toast.makeText(MemoryActivity.this, toastmsg, Toast.LENGTH_LONG).show();

                    String stans = String.format("%.2f", ans);
                    String stL = String.format("%.2f", L);

                    String stdisAll = String.valueOf(distance_all);

                    saveMemory(fileHead, sendDaytime, stdistance, stfuel, stMoney, stans, stL, setKyori, setYushu, stdisAll);

                    save_avgFuel(stans);
                    save_avgMoney(stMoney);

                    save_distance(stdisAll);

                    save_now(stans);

                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);

                }
            }
        });

    }

    public void saveMemory(String header ,String day , String Distanse , String Fuels , String Moneys , String nempi , String LMoney , String putKyori ,
                           String putYush , String putAlldis){
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
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(putAlldis.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save_avgFuel(String Fuelavg){
        try (FileOutputStream fileOutputStream = openFileOutput(allFuel, Context.MODE_PRIVATE | Context.MODE_APPEND);) {

            String enter="\n";
            fileOutputStream.write(Fuelavg.getBytes());
            fileOutputStream.write(enter.getBytes());

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save_avgMoney(String Moneyavg){
        try (FileOutputStream fileOutputStream = openFileOutput(allMoney, Context.MODE_PRIVATE | Context.MODE_APPEND);) {

            String enter="\n";
            fileOutputStream.write(Moneyavg.getBytes());
            fileOutputStream.write(enter.getBytes());

        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void save_distance(String num){

        deleteFile(Filename);

        try(FileOutputStream fileOutputStream=openFileOutput(Filename,Context.MODE_PRIVATE);){
            fileOutputStream.write(num.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readODO(String setname){
        try(FileInputStream fileInputStream = openFileInput(setname);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            String lineBuffer;

            if((lineBuffer=buffer.readLine()) !=null){
                push=lineBuffer;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return push;
    }

    public boolean numcheck(String num){
        try{
            Double.parseDouble(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public void save_now(String num){

        deleteFile("nowdata.txt");

        try(FileOutputStream fileOutputStream=openFileOutput("nowdata.txt",Context.MODE_PRIVATE);){
            fileOutputStream.write(num.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}