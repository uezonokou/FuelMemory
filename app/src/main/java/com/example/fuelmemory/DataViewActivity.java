package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataViewActivity extends Activity {

    public String day_Data;

    public String inportData[] = new String[6];

    public TextView day;
    public TextView yushyu;
    public TextView ryou;
    public TextView kyori;
    public TextView kingaku;
    public TextView fuelvalue;
    public TextView Lkingaku;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateview);

        Intent intent = getIntent();
        day_Data = intent.getStringExtra(HistoryActivity.DAY_MESSAGE);

        day=findViewById(R.id.day);
        yushyu=findViewById(R.id.Yushu);
        ryou=findViewById(R.id.ryou);
        kyori=findViewById(R.id.kyori);
        kingaku=findViewById(R.id.kingaku);
        fuelvalue=findViewById(R.id.Fuel_value);
        Lkingaku=findViewById(R.id.Lkingaku);

        String FullPath;

        FullPath = "Memory_" + day_Data + ".txt";

        inportData=readFile(FullPath);


        day.setText(inportData[0]);
        //yushyu.setText(inportData[1]);
        kyori.setText(inportData[1] + "km");
        ryou.setText(inportData[2] + "L");
        kingaku.setText(inportData[3] + "円");
        fuelvalue.setText(inportData[4]+"km/L");
        Lkingaku.setText(inportData[5] + "円");

    }



        public String[] readFile(String file) {
        int cnt = 0;
        String inportData[] = new String[6];

        try (FileInputStream fileInputStream = openFileInput(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"))) {

            String lineBuffer;
            while ((lineBuffer = reader.readLine()) != null) {
                if (cnt == 0) {
                    inportData[0] = lineBuffer;
                    cnt++;
                } else if (cnt == 1) {
                    inportData[1] = lineBuffer;
                    cnt++;
                } else if (cnt == 2) {
                    inportData[2] = lineBuffer;
                    cnt++;
                } else if (cnt == 3) {
                    inportData[3] = lineBuffer;
                    cnt++;
                }else if (cnt == 4) {
                    inportData[4] = lineBuffer;
                    cnt++;
                }else if (cnt == 5) {
                    inportData[5] = lineBuffer;
                    cnt++;
                }else if (cnt == 5) {
                    inportData[5] = lineBuffer;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return inportData;
    }


}
