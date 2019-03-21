package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    public static final String MEMORY_DATA="Memory of Data";

    public TextView Recently;
    public TextView Fuelavg;
    public TextView Moneyavg;

    public double Favg;
    public double nenpi;
    public double Mavg;

    static final int request=1000;

    public String Fuelavg_File = "avgFuel.txt";
    public String Moneyavg_File = "avgMoney.txt";

    public String[] inportFuel;

    public double AllFuel = 0;
    public double AllMoney = 0;

    public double Fuel_average;

    public String[] inportMoney;
    public double[] double_Money;

    public double Money_average;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Recently=findViewById(R.id.Recently_Fuel);

        Fuelavg=findViewById(R.id.avgFuel);

        Moneyavg=findViewById(R.id.avgMoney);

        inportFuel=readFuel(Fuelavg_File);

        /*double[] double_Fuel= new double[inportFuel.length];

        int cnt=0;
            if (inportFuel[cnt] != null) {
                for(int i=0; i<inportFuel.length; i++){

                    double_Fuel[i]=  Double.valueOf(inportFuel[i]);
                    AllFuel = AllFuel + double_Fuel[i];

                }

                Fuel_average = AllFuel / cnt - 1;
                Fuelavg.setText(String.valueOf(Fuel_average));

            }


        cnt=0;

        inportMoney=readMoney(Moneyavg_File);


        if(inportMoney[cnt] != null) {
                while (cnt <= inportMoney.length) {

                    double_Money[cnt] = Double.valueOf(inportMoney[cnt]);
                    AllMoney = AllMoney + double_Money[cnt];
                    cnt++;

                }

                Money_average = AllMoney / cnt - 1;
                Moneyavg.setText(String.valueOf(Money_average));


        }*/


        Button Start=findViewById(R.id.StartSetting);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplication(),StartActivity.class);
                startActivity(intent);
            }
        });



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

    public String[] readFuel(String setFuelfile){
        String inportFuel[] = new String[50];
        int cnt=0;

        try(FileInputStream fileInputStream = openFileInput(setFuelfile);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            String lineBuffer;

            while ((lineBuffer=buffer.readLine()) !=null){

                if(cnt==0){
                    String gomi = lineBuffer;
                    cnt++;
                }else {
                    inportFuel[cnt-1] = lineBuffer;
                    cnt++;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return inportFuel;
    }


    public String[] readMoney(String setMoneyfile){

        String inportMoney[] = new String[50];
        int cnt=0;
        try(FileInputStream fileInputStream = openFileInput(setMoneyfile);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            String lineBuffer;


            while ((lineBuffer=buffer.readLine()) !=null){

                if(cnt==0){
                    String gomi = lineBuffer;
                    cnt++;
                }else {
                    inportMoney[cnt-1] = lineBuffer;
                    cnt++;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return inportMoney;
    }


}
