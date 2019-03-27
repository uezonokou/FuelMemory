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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartActivity extends Activity {

    public EditText Carname;
    public EditText startdis;
    public EditText Memo;

    public String filename = "Startsetting.txt";
    public String ODOfile ="ODO.txt";

    public String RTData[];

    public RadioGroup seasonG;

    public EditText tank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Carname = findViewById(R.id.CarName);
        startdis = findViewById(R.id.Start_Distance);
        Memo = findViewById(R.id.memo);
        seasonG=findViewById(R.id.seasonG);
        tank=findViewById(R.id.tank);

        RTData = readFile(filename);

        if (RTData != null) {
            Carname.setText(RTData[0]);
            startdis.setText(RTData[1]);
            Memo.setText(RTData[3]);
            tank.setText(RTData[2]);
            String radioset = RTData[4];

            int radionum = Integer.parseInt(radioset);

            RadioGroup group =(RadioGroup) findViewById(R.id.seasonG);
            if(radionum==3000){
                group.check(R.id.three);
            } else if(radionum==5000){
                group.check(R.id.five);
            } else if(radionum==10000){
                group.check(R.id.ten);
            }

        }





        Button ok = findViewById(R.id.OK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Memory_Carname = Carname.getText().toString();
                String Memory_startdis = startdis.getText().toString();
                String Memory_Memo = Memo.getText().toString();
                int seasonID = seasonG.getCheckedRadioButtonId();
                String Memory_tunk = tank.getText().toString();

                if (Memory_Carname != null && Memory_startdis != null && Memory_Memo != null && seasonID != -1 && Memory_tunk != null) {

                    RadioButton season = (RadioButton) findViewById(seasonID);
                    String oilseason = season.getText().toString();

                    saveFile(filename, Memory_Carname, Memory_startdis,Memory_tunk, Memory_Memo, oilseason);
                    saveODO(ODOfile,Memory_startdis);

                    finish();
                } else {
                    Toast.makeText(StartActivity.this, "未入力の項目があります。", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void saveFile(String File, String MC, String MS, String MT, String MM, String OS) {
        try (FileOutputStream fileOutputStream = openFileOutput(File, Context.MODE_PRIVATE);) {
            String enter ="\n";
            fileOutputStream.write(MC.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MS.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MT.getBytes());
            fileOutputStream.write(enter.getBytes());
            fileOutputStream.write(MM.getBytes());
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
                } else if(cnt == 3){
                    RTData[3] = lineBuffer;
                    cnt++;
                }else if(cnt == 4){
                    RTData[4] = lineBuffer;
                    cnt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return RTData;
    }

}

