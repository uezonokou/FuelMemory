package com.example.fuelmemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends Activity {

    public static final String SendData = "null";

    public String filehead ="Memory_";

    private List<String> listDirectory = new ArrayList<>();

    private String log ="";

    public ArrayList<String> list = new ArrayList<String>();

    public static final String DAY_MESSAGE ="dayname_inside";

    private String[] pass = new String[20];

    public int cnt=0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SearchMemory();

        final ListView listView = findViewById(R.id.Memory_List);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        listView.setEmptyView(findViewById(R.id.enmpty));
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView_click = (ListView)parent;

                String selectedItemStr =(String)listView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), DataViewActivity.class);
                intent.putExtra(DAY_MESSAGE,pass[position]);
                startActivity(intent);
            }
        });


    }


    public void SearchMemory() {
        String path ="/data/data/com.example.fuelmemory/files";
        String type = "txt";

        listDirectory.add(path);

        int m = 0;
        int n = 0;
        String[] filename=null;
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
        if(mess!="Startsetting.txt" || mess!="ODO.txt"){
            if(mess.length() > 27) {
                mess = mess.substring(7, 28);
                pass[cnt]=mess;
                cnt++;
                log = mess;
                list.add(log);
            }
        }
    }



}
