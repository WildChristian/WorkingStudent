package com.example.workingstudent.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingstudent.MainActivity;
import com.example.workingstudent.R;
import com.example.workingstudent.ui.edittime.editTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**TODO Nächste Aufgaben:
 * 1. Arbeitstag
 *
 *  --> Start/EndMethoden richtig schreiben
 *  --> Die Zeiten sollen richtig in die SQL Datenbank eingetragen werden können
 *         -> Dazu Pausenzeiten und Arbeitszeiten filtern
 *         -> Datum in SQL Format parsen
 *  ----------------------------------------
 *  2.Arbeitstag
 *  Dann:
 *
 *  1) ListView klickbar machen. Die Zeiten sollen bearbeitbar sein
 *
 *  ----------------------------------------
 * 3.Arbeitstag
 *
 *  1) Mit einem "+" sollen Arbeitszeiten hinzugefügt werden können
 *
 *  ----------------------------------------
 * 4.Arbeitstag
 *
 *  Endlich gehts zur nächsten Seite "Gehaltausrechnen"
 *
 *  1) Workingtime * Stundensatz im Listview für 7/30/Monat/Gesamt - Tage
 *  2)
 *
 */



public class HomeFragment extends Fragment {

    SQLiteDatabase  myDb;
    Button startButton;
    Button breakButton;
    Button restartButton;
    ListView listView;
    TextView timeview;

    boolean timerIsActive = false;
    boolean breakIsActive = false;

    String date;
    int Pausenzeit = 0;
    int savePausenzeit = 0;
    int time = 0;
    int saveworktime = 0;
    int hours = 00;
    int minutes = 00;
    int i_timebreak;
    String _hoursAsString = "";
    String _minutesAsString = "";

    int pminutes = 00;
    String p_minutesAsString = "00";
    String p_secAsString = "00";

    ArrayList<String> listForView = new ArrayList<String>();



    WorkDay merkeWorkDay;

    public void setWorkdays(ArrayList<WorkDay> workdays) {
        this.workdays = workdays;
    }

    ArrayList<WorkDay> workdays;

    public WorkDay getMerkeWorkDay() {
        return merkeWorkDay;
    }

    public void addWorkTime(){

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        date =  formatter.format(new Date());
        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();

        try{
            myDb.execSQL("INSERT INTO times (m_workingTime,m_breakTime, m_date, m_Lohn, m_notizen) VALUES ("+saveworktime+","+savePausenzeit+", '"+date+"', 10,'ich bin max')");
            myDb.execSQL("INSERT INTO times (m_workingTime,m_breakTime, m_date, m_Lohn, m_notizen) VALUES (50000,"+savePausenzeit+", '"+date+"', 10,'ich bin max')");
        }catch(Exception e){
            System.out.println(e);
        }




    }

    public void callAllValuesFromDB(){

        Cursor c = myDb.rawQuery("SELECT * FROM times",null);

        workdays = new ArrayList<WorkDay>();

        int working_id = c.getColumnIndex("working_id");
        int workingtime_ = c.getColumnIndex("m_workingTime");
        int breakTime_ = c.getColumnIndex("m_breakTime");
        int date_ = c.getColumnIndex("m_date");
        int lohn_ =c.getColumnIndex("m_Lohn");
        int notizen_ =c.getColumnIndex("m_notizen");

        c.moveToFirst();

        System.out.println(c.getString(date_));

        while(c.moveToNext()){
            workdays.add(new WorkDay(c.getInt(working_id),Integer.parseInt(c.getString(workingtime_)),Integer.parseInt(c.getString(breakTime_)),c.getString(date_), c.getString(notizen_), Integer.parseInt(c.getString(lohn_))));
        }

        c.close();

        MyListAdapter adapter=new MyListAdapter(getActivity(), workdays);
        listView.setAdapter(adapter);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        startButton = root.findViewById(R.id.start_button);
        breakButton = root.findViewById(R.id.pause_button);
        restartButton = root.findViewById(R.id.reset_button);
        listView = root.findViewById(R.id.listview_summe);
        timeview = root.findViewById(R.id.zeit_anzeige);


        MainActivity activity = (MainActivity) getActivity();
        myDb = activity.getDb();

        try{

            listForView.clear();
            callAllValuesFromDB();
        }catch(Exception e){

        }


        final Handler handler = new Handler();
        final Handler handlerPause = new Handler();

        Runnable breakTime = new Runnable() {

            @Override
            public void run() {

                ++Pausenzeit;
                ++savePausenzeit;


                if(i_timebreak == 60){
                    ++pminutes;
                    Pausenzeit  = Pausenzeit-60;
                }

                p_minutesAsString = Integer.toString(pminutes);
                p_secAsString = Integer.toString((Pausenzeit));

                if(pminutes < 9){
                    p_minutesAsString = "0" +  p_minutesAsString;
                }

                if(Pausenzeit < 9){
                    p_secAsString = "0" +  Integer.toString(Pausenzeit);
                }

                breakButton.setText(p_minutesAsString + ":" + p_secAsString  );



                handler.postDelayed(this,1000);
            }
        };

        Runnable WorkTime = new Runnable() {
            @Override
            public void run() {
                ++time;
                ++saveworktime;


                if(time == 60){
                    ++minutes;
                }

                if(minutes == 60){
                    ++hours;
                }

                _minutesAsString = Integer.toString(minutes);
                _hoursAsString = Integer.toString((hours));

                if(minutes < 9){
                    _minutesAsString = "0" +  _minutesAsString;
                }

                if(hours < 9){
                    _hoursAsString = "0" +  _hoursAsString;
                }

                timeview.setText(_hoursAsString + " : " + _minutesAsString);
                handler.postDelayed(this,1000);
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (breakIsActive == true && timerIsActive == false) {
                                                   Toast.makeText(getActivity(), "Die Pause läüft noch. Schließ die Pause ab und mach Feierabend.", Toast.LENGTH_SHORT).show();
                                               } else {
                                                   if (timerIsActive == false) {
                                                       startButton.setText("Feierabend!");
                                                       handler.post(WorkTime);
                                                       handlerPause.removeCallbacks(breakTime);
                                                       breakButton.setText("Starte Pause!");
                                                       timerIsActive = true;
                                                       breakIsActive = true;




                                                   } else {
                                                       timerIsActive = false;
                                                       breakIsActive = false;

                                                       handler.removeCallbacks(WorkTime);


                                                       addWorkTime();


                                                       try{
                                                           listForView.clear();
                                                           callAllValuesFromDB();
                                                       }catch(Exception e){

                                                       }

                                                       time = 0;
                                                       Pausenzeit = 0;
                                                       savePausenzeit = 0;
                                                       saveworktime = 0;

                                                       startButton.setText("Start!");
                                                   }
                                               }
                                           }
                                       }
        );

        breakButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if(breakIsActive == true){
                                                   if(timerIsActive == false){
                                                       timerIsActive = true;
                                                       handler.post(WorkTime);
                                                       handler.removeCallbacks(breakTime);
                                                       breakButton.setText("Starte Pause!");
                                                   }else{
                                                       timerIsActive = false;

                                                       handler.removeCallbacks(WorkTime);
                                                       handler.post(breakTime);
                                                   }
                                               }
                                           }
                                       }
        );

        restartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(WorkTime);
                handlerPause.removeCallbacks(breakTime);
                timeview.setText("00 : 00");
                startButton.setText("START!");
                timerIsActive =false;
                breakIsActive = false;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try{
                    merkeWorkDay = workdays.get(position);
                    Intent myIntent = new Intent(getContext(), editTime.class);

                    // getContext().startActivity(myIntent);

                    myIntent.putExtra("Editing",  merkeWorkDay);
                    startActivity(myIntent);
                }catch(Exception e){
                    Log.i("e",e.toString());
                }


            }
        });


        return root;
    }
}