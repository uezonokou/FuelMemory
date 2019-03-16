package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class HistoryActivity extends Activity {

    public static final String SendData = "null";

    public String filehead ="Memory_";

    public String inportData[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*ファイルをどうやって検索するか
            記録するときに日付データも遷移時に渡してそれを受け取ってファイル名指定させる？
            /data/data/com.example.fuelmemory/files
         */

        inportData=readFile(filehead);


        ArrayList<String> list = new ArrayList<String>();

        list.add("お試し");
        list.add("記憶したデータの");
        list.add("個数分addを繰り返す");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        ListView listView = findViewById(R.id.Memory_List);
        listView.setAdapter(adapter);

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return inportData;
    }

}
