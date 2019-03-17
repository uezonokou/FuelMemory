package com.example.fuelmemory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends Activity {

    public static final String SendData = "null";

    public String filehead ="Memory_";

    public String inportData[];

    private List<String> listDirectory = new ArrayList<>();

    private String log ="";

    public ArrayList<String> list = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*ファイルをどうやって検索するか
            記録するときに日付データも遷移時に渡してそれを受け取ってファイル名指定させる？
            /data/data/com.example.fuelmemory/files
         */


        SearchMemory();
        
        
        //inportData=readFile(filehead);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        ListView listView = findViewById(R.id.Memory_List);
        listView.setAdapter(adapter);

    }


    public void SearchMemory() {
        String path ="/data/data/com.example.fuelmemory/files";
        String type = "txt";

        listDirectory.add(path);

        int m = 0;
        int n = 0;
        String[] filename;
        String txtPath =null;

        while (listDirectory.size() > m){
            File directory =new File(listDirectory.get(m));
            filename=directory.list();

            n=0;
            while (filename.length > n){
                File subfile;
                subfile=new File(directory.getPath() + "/" + filename[n]);

                if(subfile.isDirectory()){
                    Log.d("debug","isDirectory");
                    listDirectory.add(directory.getPath() + "/" + filename[n]);
                    txtPath=filename[n];
                } else if(subfile.getName().endsWith(type)){
                    Log.d("debug","getName");
                    txtPath = filename[n];

                    putLog(txtPath);
                } else {
                    putLog("nothing to do");
                }
                n++;
            }
            m++;
        }

    }

    public void putLog(String mess){
        if(mess!="Startsetting.txt"){
            if(mess.length() > 27) {
                mess = mess.substring(7, 28);
                log = mess;
                list.add(log);
            }
        }
    }


   /* public String[] readFile(String file) {
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return inportData;
    }*/
}
