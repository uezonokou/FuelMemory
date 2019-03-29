package com.example.fuelmemory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartActivity extends Activity {

    public EditText startdis;

    public String filename = "Startsetting.txt";
    public String ODOfile ="ODO.txt";

    public String RTData[];

    public RadioGroup seasonG;

    public EditText tank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startdis = findViewById(R.id.Start_Distance);
        seasonG=findViewById(R.id.seasonG);
        tank=findViewById(R.id.tank);

        File startfile =this.getFileStreamPath("Startsetting.txt");
        boolean startExists = startfile.exists();

        if(startExists!=false) {
            RTData = readFile(filename);

            if (RTData != null) {
                startdis.setText(RTData[0]);
                tank.setText(RTData[1]);
                String radioset = RTData[2];

                int radionum = Integer.parseInt(radioset);

                RadioGroup group = (RadioGroup) findViewById(R.id.seasonG);
                if (radionum == 3000) {
                    group.check(R.id.three);
                } else if (radionum == 5000) {
                    group.check(R.id.five);
                } else if (radionum == 10000) {
                    group.check(R.id.ten);
                }

            }
        }





        Button ok = findViewById(R.id.OK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Memory_startdis = startdis.getText().toString();
                int seasonID = seasonG.getCheckedRadioButtonId();
                String Memory_tunk = tank.getText().toString();

                if (Memory_startdis != null && seasonID != -1 && Memory_tunk != null) {

                    RadioButton season = (RadioButton) findViewById(seasonID);
                    String oilseason = season.getText().toString();

                    saveFile(filename, Memory_startdis,Memory_tunk, oilseason);
                    saveODO(ODOfile,Memory_startdis);

                    finish();
                } else {
                    Toast.makeText(StartActivity.this, "未入力の項目があります。", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void saveFile(String File, String MS, String MT, String OS) {
        try (FileOutputStream fileOutputStream = openFileOutput(File, Context.MODE_PRIVATE);) {
            String enter ="\n";
            fileOutputStream.write(MS.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MT.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(OS.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveODO(String saveODO ,String StartODO){
        try(FileOutputStream fileOutputStream=openFileOutput(saveODO,Context.MODE_PRIVATE);){
            fileOutputStream.write(StartODO.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public String[] readFile(String file) {
        int cnt = 0;
        String RTData[] = new String[5];

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
                    cnt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return RTData;
    }

}

