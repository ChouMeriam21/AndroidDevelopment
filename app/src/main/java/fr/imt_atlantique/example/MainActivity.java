package fr.imt_atlantique.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText txtNom = null;
    private EditText txtPrenom = null;
    private EditText txtDateNaissance = null;

    private Button btnCalendar;
    private EditText txtVilleNaissance = null;
    private EditText txtTel = null;
    private Button btnValidate;

    private Button btnAjouter;

    private LinearLayout layout;
    private Spinner txtDepartement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Lifecycle", "onCreate method");

        txtNom = (EditText) findViewById(R.id.txtNom);
        txtPrenom = (EditText) findViewById(R.id.txtPrenom);
        txtDateNaissance = (EditText) findViewById(R.id.txtDateNaissance);
        btnCalendar = (Button) findViewById(R.id.btnDate);
        txtVilleNaissance = (EditText) findViewById(R.id.txtVilleNaissance);
        btnValidate = (Button) findViewById(R.id.btnValidate);
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        txtTel = (EditText) findViewById(R.id.txtNumTel);
        layout = (LinearLayout)findViewById(R.id.linearLayout);
        txtDepartement = (Spinner) findViewById(R.id.txtDepartement);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                int numberTel = layout.getChildCount();
                String textToShow;
                textToShow = txtNom.getText().toString()+"\n"+txtPrenom.getText().toString()+"\n"+txtDateNaissance.getText().toString()+"\n"+txtVilleNaissance.getText().toString()+"\n"+txtDepartement.getSelectedItem().toString()+"\n"+numberTel;
                Snackbar b1 = Snackbar.make(findViewById(R.id.constraintLayout), textToShow, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS", new View.OnClickListener() {
                    public void onClick(View v) {
                        Snackbar b2 = Snackbar.make(findViewById(R.id.constraintLayout), "Dismissed Successfully", Snackbar.LENGTH_SHORT);
                        b2.show();
                    }
                });
                View snackbarView = b1.getView();
                TextView snackTextView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                snackTextView.setMaxLines(6);
                b1.show();
            }
        });

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txtTelText = txtTel.getText().toString();
                LinearLayout subLayout = new LinearLayout(getBaseContext());
                subLayout.setOrientation(LinearLayout.HORIZONTAL);
                Button delete = new Button(getBaseContext());
                delete.setText("SUPPRIMER");
                TextView text = new TextView(getBaseContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setText(txtTelText);
                subLayout.addView(text);
                subLayout.addView(delete);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
                int newHeight = layout.getHeight() + dpToPx(45);
                layoutParams.height = newHeight;
                layout.setLayoutParams(layoutParams);
                layout.addView(subLayout);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        layout.removeView((View) delete.getParent());
                        int newHeight = layout.getHeight() - dpToPx(45);
                        layoutParams.height = newHeight;
                        layout.setLayoutParams(layoutParams);
                    }
                });
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
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
        String textDate = dayOfMonth+"/"+month+"/"+year;
        txtDateNaissance.setText(textDate);
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void resetAction(MenuItem item) {
        txtNom.setText("");
        txtPrenom.setText("");
        txtDateNaissance.setText("");
        txtVilleNaissance.setText("");
        txtTel.setText("");
        layout.removeAllViewsInLayout();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
        layoutParams.height = dpToPx(45);
        layout.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart method");
    }

    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
    }

    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop method");
    }

    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume method");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy method");
    }
}