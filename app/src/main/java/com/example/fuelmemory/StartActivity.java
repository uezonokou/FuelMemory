package com.example.fuelmemory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartActivity extends Activity {

    public EditText Carname;
    public EditText startdis;
    public EditText Memo;

    public String filename = "Startsetting.txt";

    public String RTData[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Carname = findViewById(R.id.CarName);
        startdis = findViewById(R.id.Start_Distance);
        Memo = findViewById(R.id.memo);

        RTData = readFile(filename);

        if (RTData != null) {
            Carname.setText(RTData[0]);
            startdis.setText(RTData[1]);
            Memo.setText(RTData[2]);
        }



        Button ok = findViewById(R.id.OK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Memory_Carname = Carname.getText().toString();
                String Memory_startdis = startdis.getText().toString();
                String Memory_Memo = Memo.getText().toString();

                if (Memory_Carname != null && Memory_startdis != null && Memory_Memo != null) {

                    saveFile(filename, Memory_Carname, Memory_startdis, Memory_Memo);
                    finish();
                } else {
                    Toast.makeText(StartActivity.this, "未入力の項目があります。", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void saveFile(String File, String MC, String MS, String MM) {
        try (FileOutputStream fileOutputStream = openFileOutput(File, Context.MODE_PRIVATE);) {
            String enter ="\n";
            fileOutputStream.write(MC.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MS.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MM.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String[] readFile(String file) {
        int cnt = 0;
        String RTData[] = new String[3];

        try (FileInputStream fileInputStream = openFileInput(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"))) {

            String lineBuffer;
            while ((lineBuffer = reader.readLine()) != null) {
                if (cnt == 0) {
                    RTData[0] = lineBuffer;
                    cnt++;
                } else if (cnt == 1) {
                    RTData[1] = lineBuffer;
                    cnt++;
                } else if (cnt == 2) {
                    RTData[2] = lineBuffer;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return RTData;
    }

}

