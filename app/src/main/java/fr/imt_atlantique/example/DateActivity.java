package fr.imt_atlantique.example;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView text;

    Button btnCalendar;
    Button btnDateValidate;
    Button btnDateCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_activity);
        text= findViewById(R.id.txtDateNaissance);
        btnCalendar = findViewById(R.id.btnDate);
        btnDateValidate = findViewById(R.id.btnDateValidate);
        btnDateCancel = findViewById(R.id.btnDateCancel);

        showDatePickerDialog();


        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }

        });

        btnDateValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textDate = text.getText().toString();
                String code = "RESULT_OK";
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                intent.putExtra("date",textDate);
                //intent.putExtra("code",code);
                //startActivity(intent);
                startActivityForResult(intent, RESULT_OK);
            }
        });

        btnDateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = "RESULT_CANCEL";
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                //intent.putExtra("code",code);
                //startActivity(intent);
                startActivityForResult(intent, RESULT_CANCELED);
            }
        });
    }


    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
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
    }
}
