package com.example.workingstudent.ui.edittime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.workingstudent.MainActivity;
import com.example.workingstudent.ui.home.HomeFragment;
import com.example.workingstudent.ui.home.MyListAdapter;
import com.example.workingstudent.ui.home.WorkDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workingstudent.R;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class editTime extends AppCompatActivity {


    /**
     * TODO
     *
     * @param savedInstanceState
     * <p>
     * VON BIS muss vorhanden sein. Richte dich nach der anderen App
     */

    SQLiteDatabase myDb;
    Button speichernButton;
    Button löschenButton;
    Switch löschenswitch;
    EditText datumedit;
    EditText arbeitszeitedit;
    EditText pausenzeitedit;
    EditText verdienstedit;
    EditText kommentaredit;

    WorkDay merkeWorkday;
    boolean Switchdeleate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_time);

            Intent intent = getIntent();
            merkeWorkday = (WorkDay) intent.getExtras().getSerializable("Editing");

            System.out.println(merkeWorkday.getM_date());

            speichernButton = findViewById(R.id.speichern_button);
            löschenButton = findViewById(R.id.löschen_button);
            löschenswitch = findViewById(R.id.switch1_toogle_löschen);
            datumedit = findViewById(R.id.datum_edit);
            arbeitszeitedit = findViewById(R.id.arbeitszeit_edit);
            pausenzeitedit = findViewById(R.id.pausenzeit_edit);
            verdienstedit = findViewById(R.id.verdienst_edit);
            kommentaredit = findViewById(R.id.kommentar_edit);

            setAllAttributes(merkeWorkday);

            myDb = MainActivity.db;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setAllAttributes(WorkDay a) {
        datumedit.setText(a.getM_date());
        arbeitszeitedit.setText(Integer.toString(a.getM_workingTime()));
        pausenzeitedit.setText(Integer.toString(a.getM_breakTime()));
        verdienstedit.setText(Integer.toString(a.getM_Lohn() * (a.getM_workingTime() / 3600)));
        kommentaredit.setText(a.getM_notizen());
    }

    public void saveAllAttributes(View view) {

        int updatedWorkingTime = Integer.parseInt(arbeitszeitedit.getText().toString());
        int updatedbreakTime_ = Integer.parseInt(pausenzeitedit.getText().toString());
        double updatedverdinenst = Integer.parseInt(verdienstedit.getText().toString());
        String updatedkommentar = kommentaredit.getText().toString();

        try {
            myDb.execSQL("UPDATE times SET m_workingTime = " + updatedWorkingTime + ", m_breakTime = " + updatedbreakTime_ + ",m_Lohn = " + updatedverdinenst + ", m_notizen = '" + updatedkommentar + "' WHERE working_id = " + merkeWorkday.getId() + ";");

            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);

        } catch (Exception e) {
            Log.i("e", e.toString());
        }
    }

    public void deleteWorkDay(View view) {
        try {

            if(Switchdeleate == true){
                myDb.execSQL("DELETE FROM times Where  working_id = "+merkeWorkday.getId()+"");

                Intent myIntent = new Intent(this, MainActivity.class);
                startActivity(myIntent);
            }else {
                Toast.makeText(getApplicationContext(), "Bist du dir Sicher den Eintrag unwiderruflich zu löschen? Falls ja, klicke einmal auf den Switch.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    public void activedSwitch(View view) {

        try {
            if(Switchdeleate)
                Switchdeleate = false;
            else {
                Switchdeleate = true;
            }
        } catch (Exception e) {

        }
    }
}