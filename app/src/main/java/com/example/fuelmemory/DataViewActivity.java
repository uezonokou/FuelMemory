package com.example.fuelmemory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataViewActivity extends Activity {

    public String day_Data;

    public String inportData[] = new String[10];

    public TextView day;
    public TextView yushyu;
    public TextView ryou;
    public TextView kyori;
    public TextView kingaku;
    public TextView fuelvalue;
    public TextView Lkingaku;
    public TextView kyoriset;
    public TextView ODO;
    public Button back;
    public Button delete;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateview);

        final Intent intent = getIntent();
        day_Data = intent.getStringExtra(HistoryActivity.DAY_MESSAGE);

        day=findViewById(R.id.day);
        yushyu=findViewById(R.id.Yushu);
        ryou=findViewById(R.id.ryou);
        kyori=findViewById(R.id.kyori);
        kingaku=findViewById(R.id.kingaku);
        fuelvalue=findViewById(R.id.Fuel_value);
        Lkingaku=findViewById(R.id.Lkingaku);
        kyoriset=findViewById(R.id.Kyoriset);
        back=findViewById(R.id.back);
        ODO=findViewById(R.id.ODO);
        delete=findViewById(R.id.delete);


        final String FullPath;

        FullPath = "Memory_" + day_Data + ".txt";

        inportData=readFile(FullPath);


        day.setText(inportData[0]);
        yushyu.setText(inportData[7]);
        kyori.setText(inportData[1] + "km");
        ryou.setText(inportData[2] + "L");
        kingaku.setText(inportData[3] + "円");
        fuelvalue.setText(inportData[4]+"km/L");
        Lkingaku.setText(inportData[5] + "円");
        kyoriset.setText(inportData[6]);
        ODO.setText(inportData[8] + "km");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplication(),HistoryActivity.class);

                AlertDialog.Builder builder=new AlertDialog.Builder(DataViewActivity.this);
                builder.setMessage("削除してもいいですか？").setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(FullPath);
                        startActivity(intent);
                    }
                }).setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });


    }



        public String[] readFile(String file) {
        int cnt = 0;
        String inportData[] = new String[10];

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
                    cnt++;
                } else  if(cnt == 6){
                    inportData[6] = lineBuffer;
                    cnt++;
                } else if(cnt == 7){
                    inportData[7] = lineBuffer;
                    cnt++;
                } else if(cnt==8){
                    inportData[8] = lineBuffer;
                    cnt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return inportData;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


}
