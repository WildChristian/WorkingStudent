package com.example.workingstudent.ui.dashboard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.workingstudent.MainActivity;
import com.example.workingstudent.R;
import com.example.workingstudent.ui.home.WorkDay;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DashboardFragment extends Fragment {

    SQLiteDatabase myDb;
    Button siebenTageButton;
    Button dreißigTageButton;
    Button aktuellerMonatButton;
    ListView SummenListView;
    TextView SummeText;

    private DashboardViewModel dashboardViewModel;

    public void SelectAbfrageNachTagen(int a) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Getting current date
        Calendar cal = Calendar.getInstance();
        //Number of Days to add
        cal.add(Calendar.DAY_OF_MONTH, -a);
        String newDate = sdf.format(cal.getTime());

        try{
            Cursor c = myDb.rawQuery("SELECT * FROM times where m_date > '"+ newDate +"' ",null);

            int working_id = c.getColumnIndex("working_id");
            int workingtime_ = c.getColumnIndex("m_workingTime");
            int breakTime_ = c.getColumnIndex("m_breakTime");
            int date_ = c.getColumnIndex("m_date");
            int lohn_ =c.getColumnIndex("m_Lohn");
            int notizen_ =c.getColumnIndex("m_notizen");

            c.moveToFirst();

            System.out.println(c.getString(date_));

            ArrayList<WorkDay> workdays = new ArrayList<WorkDay>();
            ArrayList<String> summenliste = new ArrayList<String>() ;
            while(c.moveToNext()){
                workdays.add(new WorkDay(c.getInt(working_id),Integer.parseInt(c.getString(workingtime_)),Integer.parseInt(c.getString(breakTime_)),c.getString(date_), c.getString(notizen_), Integer.parseInt(c.getString(lohn_))));
            }

            c.close();
            int summe = 0;
            for(int i = 0 ; i < workdays.size();++i){
                summenliste.add("Deine Arbeitszeit betrug: " + workdays.get(i).getM_workingTime() + " : " + workdays.get(i).getM_breakTime() + "\n" +  workdays.get(i).getM_date() + "\n" +  workdays.get(i).getM_notizen());
                summe +=  (workdays.get(i).getM_workingTime()/3600)*10;
            }

            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, summenliste);
            SummenListView.setAdapter(mArrayAdapter);
            SummeText.setText("Summe:   " + summe + " €");
        }catch(Exception e){
            System.out.println((e));
        }




    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        siebenTageButton = root.findViewById(R.id.sieben_tage_button);
        dreißigTageButton = root.findViewById(R.id.dreißig_tage_button);
        aktuellerMonatButton = root.findViewById(R.id.aktueller_monat);
        SummenListView = root.findViewById(R.id.listview_summe);
        SummeText = root.findViewById(R.id.textview_summe);

        MainActivity activity = (MainActivity) getActivity();
        myDb = activity.getDb();



        siebenTageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SelectAbfrageNachTagen(7);
            }

        });

        dreißigTageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SelectAbfrageNachTagen(30);
            }

        });

        aktuellerMonatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DateFormat formatter = new SimpleDateFormat("dd");
                int date = Integer.parseInt(formatter.format(new Date()));

                SelectAbfrageNachTagen(date);
            }

        });


        return root;
    }
}