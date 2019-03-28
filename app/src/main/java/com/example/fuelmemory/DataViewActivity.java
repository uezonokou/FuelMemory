package com.example.fuelmemory;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DataViewActivity extends AppCompatActivity {

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
    public Button datapushing;

    public Button mail;

    public String FullPath;

    private final int REQUEST_PERMISSION = 1000;

    public String outputPath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateview);

        final Intent intent = getIntent();
        day_Data = intent.getStringExtra(HistoryActivity.DAY_MESSAGE);

        day = findViewById(R.id.day);
        yushyu = findViewById(R.id.Yushu);
        ryou = findViewById(R.id.ryou);
        kyori = findViewById(R.id.kyori);
        kingaku = findViewById(R.id.kingaku);
        fuelvalue = findViewById(R.id.Fuel_value);
        Lkingaku = findViewById(R.id.Lkingaku);
        kyoriset = findViewById(R.id.Kyoriset);
        back = findViewById(R.id.back);
        ODO = findViewById(R.id.ODO);
        delete = findViewById(R.id.delete);
        datapushing=findViewById(R.id.pushOut);
        mail=findViewById(R.id.mailer);

        FullPath = "Memory_" + day_Data + ".txt";

        inportData = readFile(FullPath);


        day.setText(inportData[0]);
        yushyu.setText(inportData[7]);
        kyori.setText(inportData[1] + "km");
        ryou.setText(inportData[2] + "L");
        kingaku.setText(inportData[3] + "円");
        fuelvalue.setText(inportData[4] + "km/L");
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
                final Intent intent = new Intent(getApplication(), HistoryActivity.class);

                AlertDialog.Builder builder = new AlertDialog.Builder(DataViewActivity.this);
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


        datapushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2=new AlertDialog.Builder(DataViewActivity.this);
                builder2.setMessage("このデータを出力します。\nデータの書き込みを許可してください。").setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pushOut();
                    }
                }).setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder2.show();
            }
        });


       mail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setAction(intent.ACTION_SEND);
               String[] address ={"kou.07221202@gmail.com"};

               intent.putExtra(Intent.EXTRA_EMAIL,address);
               intent.putExtra(Intent.EXTRA_SUBJECT,"TEST");

               intent.setType("text/plain");
               intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory() + "/" + FullPath));
               startActivity(intent);

            //SDカードに出力するには本体ストレージ内のsdcardを読んでも意味がない。
               //きちんとしたSDカードのパスと権限が必要

           }
       });



    }

    public String[] readFile(String file) {
        int cnt = 0;
        String inportData[] = new String[9];

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
                } else if (cnt == 4) {
                    inportData[4] = lineBuffer;
                    cnt++;
                } else if (cnt == 5) {
                    inportData[5] = lineBuffer;
                    cnt++;
                } else if (cnt == 5) {
                    inportData[5] = lineBuffer;
                    cnt++;
                } else if (cnt == 6) {
                    inportData[6] = lineBuffer;
                    cnt++;
                } else if (cnt == 7) {
                    inportData[7] = lineBuffer;
                    cnt++;
                } else if (cnt == 8) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void pushOut(){

        outputPath= Environment.getExternalStorageDirectory() + "/" + FullPath;

        if(Build.VERSION.SDK_INT >= 23){
            checkPermission();
        } else{
            writefile();
        }
    }

    private void writefile(){

        if(isEternalStrageWriteable()){
            File file = new File(outputPath);

            File sdDir = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DCIM),FullPath);
            /*/File[] SDfile = getExternalFilesDirs(null);
            for(File dir : SDfile){
                if(Environment.isExternalStorageRemovable(dir));
                sdDir = new File(dir + "/" + FullPath);
                break;
            }*/


            try(FileOutputStream fileOutputStream = new FileOutputStream(sdDir,true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
                BufferedWriter bw = new BufferedWriter(outputStreamWriter);){

                    String enter="\n";
                    bw.write("日付 : " + inportData[0]);
                    bw.flush();
                    bw.write(enter);
                    bw.flush();
                    bw.write("燃費 : " + inportData[4] + "km/L");
                    bw.flush();
                    bw.write(enter);
                    bw.flush();
                    bw.write("走行距離 : " + inportData[1]);
                    bw.flush();
                    bw.write(enter);
                    bw.flush();
                    bw.write("給油量 : " + inportData[2] + "L");
                    bw.flush();
                    bw.write(enter);
                    bw.flush();
                    bw.write("金額 : " + inportData[5] + "円");
                    bw.flush();

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("データを下記の場所に出力しました。\n\n" + sdDir).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.show();

            }catch (Exception e){
                AlertDialog.Builder builder1 =new AlertDialog.Builder(this);
                builder1.setMessage("書き込みエラー").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder1.show();

                e.printStackTrace();
            }
        }

    }

    public boolean isEternalStrageWriteable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }


    public void checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            writefile();
        }
        else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(DataViewActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            Toast toast = Toast.makeText(this,"データ出力に許可が必要です",Toast.LENGTH_LONG);
            toast.show();

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode==REQUEST_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                writefile();
            } else {
                Toast toast=Toast.makeText(this,"データを出力できません",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
