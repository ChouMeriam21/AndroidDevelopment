package fr.imt_atlantique.example;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView text;

    Boolean ButtonValidateClicked=false;
    Button btnCalendar;
    Button btnDateValidate;
    Button btnDateCancel;

    SharedPreferences preferences;

    String lastDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_date, container, false);

        preferences = this.getActivity().getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        lastDate = preferences.getString("date","");
        // Inflate the layout for this fragment
        text= root.findViewById(R.id.txtDateNaissance);
        text.setText(preferences.getString("date_picker",""));
        btnCalendar = root.findViewById(R.id.btnDate);
        btnDateValidate = root.findViewById(R.id.btnDateValidate);
        btnDateCancel = root.findViewById(R.id.btnDateCancel);


        if (ButtonValidateClicked == true){
            showDatePickerDialog();
        }

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }

        });

        btnDateValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textDate = text.getText().toString();
                ((MainActivity) getActivity()).onDateUser(textDate);
            }
        });

        btnDateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onDateUser(lastDate);
            }
        });
        return root;
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this.getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String textDate = dayOfMonth+"/"+(month+1)+"/"+year;
        text.setText(textDate);
        SharedPreferences.Editor pfe = preferences.edit();
        pfe.putString("date_picker", textDate);
        pfe.apply();
    }

    public interface OnDatePicker {
        void onDateUser(String date);
    }

}