package com.example.workingstudent.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.workingstudent.R;
import com.example.workingstudent.ui.edittime.editTime;
import com.example.workingstudent.ui.überDieApp.UeberDieApp;

public class NotificationsFragment extends Fragment {

    TextView sendEmail;
    TextView ueberDieApp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        sendEmail = root.findViewById(R.id.email_view);
        ueberDieApp = root.findViewById(R.id.über_die_appTextView);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        ueberDieApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), UeberDieApp.class);
                startActivity(myIntent);
            }
        });


        return root;


    }


    public void sendEmail() {

        try {
            String[] adress = {"christian-wild@hotmail.de"};
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, adress);

            startActivity(intent);

        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
