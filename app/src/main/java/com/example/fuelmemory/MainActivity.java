package com.example.fuelmemory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {


    public TextView Recently;
    public TextView Fuelavg;
    public TextView Moneyavg;
    public TextView kouzokudis;

    public String stMoneyAverage;

    public String allFuel="AllFuel.txt";
    public String allMoney ="AllMoney.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Recently=findViewById(R.id.Recently_Fuel);

        Fuelavg=findViewById(R.id.avgFuel);

        Moneyavg=findViewById(R.id.avgMoney);

        kouzokudis=findViewById(R.id.kouzoku);


        File startfile =this.getFileStreamPath("Startsetting.txt");
        boolean startExists = startfile.exists();

        if(startExists==false){
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("初期設定を行ってください。").setPositiveButton("進む", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplication(),StartActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }


        File fuelalldata =this.getFileStreamPath(allFuel);
        final boolean fueldataExists = fuelalldata.exists();

        File moneyalldata =this.getFileStreamPath(allMoney);
        final boolean moneydataExists = moneyalldata.exists();

        File nowfile =this.getFileStreamPath("nowdata.txt");
        final boolean nowdataExists = nowfile.exists();

        double avgstorage = 0.0;

        if(fueldataExists!=false && moneydataExists!=false || nowdataExists!=false) {
            String[] StcaFuel = readAllFuel(allFuel);
            double[] caFuel = new double[StcaFuel.length];
            for (int i = 0; i < StcaFuel.length; i++) {
                    caFuel[i] = Double.parseDouble(StcaFuel[i]);
            }

            double Fuelaverage = average(caFuel);

            String stFuelaverage = String.format("%.2f", Fuelaverage);

            String[] StcaMoney = readAllMoney(allMoney);
            double[] caMoney = new double[StcaMoney.length];
            for (int k = 0; k < StcaMoney.length; k++) {
                    caMoney[k] = Double.parseDouble(StcaMoney[k]);
            }

           double allMoneyavg = average(caMoney);

            stMoneyAverage=String.format("%.2f",allMoneyavg);

            String nowFuel = readnow();

            Recently.setText(nowFuel+"km/l");
            Fuelavg.setText(stFuelaverage+"km/l");
            Moneyavg.setText(stMoneyAverage+"円");

            String[] start = readStart("Startsetting.txt");
            double startODO=Double.parseDouble(start[1]);
            double oilmaneger=Double.parseDouble(start[4]);

            String stOOD = readODO();
            double ODO = Double.parseDouble(stOOD);

            if(ODO % oilmaneger == 0){
                Toast.makeText(MainActivity.this, "エンジンオイルの交換時期です。", Toast.LENGTH_LONG).show();
            }


            double tunk = Double.parseDouble(start[2]);

            double kouzoku = Fuelaverage * tunk;

            String stKouzoku = String.valueOf(kouzoku);

            kouzokudis.setText("約" + stKouzoku + "km");


        } else{
            Recently.setText("---");
            Fuelavg.setText("---");
            Moneyavg.setText("---");
            kouzokudis.setText("---");
        }


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
                startActivity(intent);
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




    public String[] readAllFuel(String Setfilename){
        int datanum=datacnt(Setfilename);

        String[] dataFuel = new String[datanum];
        String linebuffer;
        int cnt=0;

        try(FileInputStream fileInputStream = openFileInput(Setfilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            while (cnt < datanum) {
                if((linebuffer=reader.readLine())!=null) {
                    dataFuel[cnt] = linebuffer;
                    cnt++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return dataFuel;
    }

    public String[] readAllMoney(String Setfilename){
        int datanum=datacnt(Setfilename);

        String[] dataMoney=new String[datanum];
        String linebuffer;
        int cnt=0;

        try(FileInputStream fileInputStream = openFileInput(Setfilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            while (cnt<datanum) {
                if((linebuffer=reader.readLine())!=null) {
                    dataMoney[cnt] = linebuffer;
                    cnt++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return dataMoney;
    }

    public String readnow(){
        String linebuffer;
        String now=null;


        try(FileInputStream fileInputStream = openFileInput("nowdata.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){

            if((linebuffer=reader.readLine())!=null) {
                now = linebuffer;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return now;
    }

    public String[] readStart(String Setfilename){
        int datanum=datacnt(Setfilename);

        String[] dataStart = new String[datanum];
        String linebuffer;
        int cnt=0;

        try(FileInputStream fileInputStream = openFileInput(Setfilename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){
            while (cnt < datanum) {
                if((linebuffer=reader.readLine())!=null) {
                    dataStart[cnt] = linebuffer;
                    cnt++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return dataStart;
    }

    public String readODO(){
        String linebuffer;
        String ODO=null;


        try(FileInputStream fileInputStream = openFileInput("ODO.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"))){

            if((linebuffer=reader.readLine())!=null) {
                ODO = linebuffer;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return ODO;
    }

    public int datacnt(String filename){
        int cnt=0;
        try(FileReader fileReader = new FileReader("/data/data/com.example.fuelmemory/files/"+filename);
            BufferedReader reader = new BufferedReader(fileReader);){
            String line;
            while ((line=reader.readLine())!=null){
                cnt++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return cnt;
    }

    public static double average(double...numbers){
        double sum=0.0;
        for(double i:numbers){
            sum += i;

        }
        return numbers.length != 0 ? sum / numbers.length : sum ;
    }

}
