package fr.imt_atlantique.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText txtNom = null;
    private EditText txtPrenom = null;
    private TextView txtDateNaissance = null;

    private Button btnCalendar;
    private EditText txtVilleNaissance = null;
    private EditText txtTel = null;
    private Button btnValidate;

    private Button btnAjouter;

    private LinearLayout layout;
    private Spinner txtDepartement;

    private SharedPreferences utilisateur;

    private String NomUtilisateur;
    private String PrenomUtilisateur;
    private String VilleUtilisateur;
    private String DateUtilisateur;
    private int DepUtilisateur;
    private Set<String> TelUtilisateur;

    private Set<String> telSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Lifecycle", "onCreate method");

        txtNom = (EditText) findViewById(R.id.txtNom);
        txtPrenom = (EditText) findViewById(R.id.txtPrenom);
        txtDateNaissance = (TextView) findViewById(R.id.txtDateNaissance);
        btnCalendar = (Button) findViewById(R.id.btnDate);
        txtVilleNaissance = (EditText) findViewById(R.id.txtVilleNaissance);
        btnValidate = (Button) findViewById(R.id.btnValidate);
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        txtTel = (EditText) findViewById(R.id.txtNumTel);
        layout = (LinearLayout)findViewById(R.id.linearLayout);
        txtDepartement = (Spinner) findViewById(R.id.txtDepartement);
        telSet = new HashSet<String>();

        utilisateur= getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
        NomUtilisateur= utilisateur.getString("nom","");
        PrenomUtilisateur= utilisateur.getString("prenom","");
        DateUtilisateur= utilisateur.getString("date","");
        VilleUtilisateur= utilisateur.getString("ville","");
        DepUtilisateur= utilisateur.getInt("department",0);
        TelUtilisateur=utilisateur.getStringSet("tel", (Set<String>) new HashSet<String>());

        txtNom.setText(NomUtilisateur);
        txtPrenom.setText(PrenomUtilisateur);
        //txtDateNaissance.setText(DateUtilisateur);
        txtVilleNaissance.setText(VilleUtilisateur);
        //txtDepartement.setSelection(2);
        txtDepartement.setSelection(DepUtilisateur);

        //displaying telephone numbers we get from preferences xml file
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
        int count = TelUtilisateur.toArray().length;
        int newHeight = layout.getHeight() + count * dpToPx(60);
        layoutParams.height = newHeight;
        layout.setLayoutParams(layoutParams);

        for (int i=0; i<TelUtilisateur.toArray().length; i++){
            txtTel.setText((String) Array.get(TelUtilisateur.toArray(),i));
            Log.i("r",(String) Array.get(TelUtilisateur.toArray(),i));
            //btnAjouter.callOnClick();
            String txtTelText = (String) Array.get(TelUtilisateur.toArray(),i);
            telSet.add(txtTelText);
            Log.i("rtelSet",(String) Array.get(telSet.toArray(),i));
            LinearLayout subLayout = new LinearLayout(getBaseContext());
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            Button delete = new Button(getBaseContext());
            Button compose = new Button(getBaseContext());
            delete.setText("SUPPRIMER");
            compose.setText("COMPOSER");
            TextView text = new TextView(getBaseContext());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text.setText(txtTelText);
            Log.i("rtext",text.getText().toString());
            subLayout.addView(text);
            subLayout.addView(delete);
            subLayout.addView(compose);
            /**LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
            int newHeight = layout.getHeight() + dpToPx(60);
            layoutParams.height = newHeight;
            layout.setLayoutParams(layoutParams);**/
            layout.addView(subLayout);
            //Log.i("Layout Height", String.valueOf(newHeight));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telSet.remove(txtTelText);
                    layout.removeView((View) delete.getParent());
                    int newHeight = layout.getHeight() - dpToPx(60);
                    layoutParams.height = newHeight;
                    layout.setLayoutParams(layoutParams);
                }
            });

            compose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txtTelText));
                    startActivity(intent);
                }
            });
        }

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

                Intent intent = new Intent(MainActivity.this, DisplayUserActivity.class);
                User user = new User(txtNom.getText().toString(), txtPrenom.getText().toString(), txtDateNaissance.getText().toString(),
                        txtVilleNaissance.getText().toString(),txtDepartement.getSelectedItem().toString(), (String[]) telSet.toArray(new String[telSet.size()]));
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

         btnAjouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txtTelText = txtTel.getText().toString();
                telSet.add(txtTelText);
                LinearLayout subLayout = new LinearLayout(getBaseContext());
                subLayout.setOrientation(LinearLayout.HORIZONTAL);
                Button delete = new Button(getBaseContext());
                Button compose = new Button(getBaseContext());
                delete.setText("SUPPRIMER");
                compose.setText("COMPOSER");
                TextView text = new TextView(getBaseContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setText(txtTelText);
                subLayout.addView(text);
                subLayout.addView(delete);
                subLayout.addView(compose);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
                int newHeight = layout.getHeight() + dpToPx(60);
                layoutParams.height = newHeight;
                layout.setLayoutParams(layoutParams);
                layout.addView(subLayout);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        telSet.remove(txtTelText);
                        layout.removeView((View) delete.getParent());
                        int newHeight = layout.getHeight() - dpToPx(60);
                        layoutParams.height = newHeight;
                        layout.setLayoutParams(layoutParams);
                    }
                });

                compose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txtTelText));
                        startActivity(intent);
                    }
                });
                txtTel.setText("");
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDatePickerDialog();
                Intent intent = new Intent(Intent.ACTION_PICK);
                startActivity(intent);
            }
        });

        getDateFromActivity();

    }



    public void getDateFromActivity() {
        String code = getIntent().getStringExtra("code");
        String dat = getIntent().getStringExtra("date");
        String codeOk= "RESULT_OK";
        if (code == null) {
            Log.i("null", "wayli null");
            txtDateNaissance.setText(DateUtilisateur);
        }

        else if (code.equals(codeOk)) {
            String date = getIntent().getStringExtra("date");
            txtDateNaissance.setText(date);
            Log.i("code",code);
            Log.i("date",date);
        }
        else {
            Log.i("null", "wayli non null");
            Boolean bool= code == codeOk;
            Log.i("null", String.valueOf(bool));
            Log.i("code", code);
            Log.i("type code", String.valueOf(code.getClass()));
            Log.i("type codeOk", String.valueOf(codeOk.getClass()));
            txtDateNaissance.setText(DateUtilisateur);
        }
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
        txtDepartement.setSelection(0);
        layout.removeAllViewsInLayout();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
        layoutParams.height = dpToPx(45);
        layout.setLayoutParams(layoutParams);
        telSet = new HashSet<String>();
    }

    public void invokeWiki(MenuItem item) {
        Uri uri = Uri.parse("http://fr.wikipedia.org/?search="+txtVilleNaissance.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void shareVilleNaissance(MenuItem item) {
        String text = txtVilleNaissance.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Partager votre ville de naissance avec :"));
        //startActivity(shareIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart method");
        getDateFromActivity();
    }

    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
        Save();
    }

    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop method");
        Save();
    }

    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume method");
        getDateFromActivity();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy method");
        Save();
    }

    public void Save() {
        SharedPreferences.Editor pfe = utilisateur.edit();
        pfe.putString("nom", txtNom.getText().toString());
        pfe.putString("prenom", txtPrenom.getText().toString());
        pfe.putString("date", txtDateNaissance.getText().toString());
        pfe.putString("ville", txtVilleNaissance.getText().toString());
        pfe.putInt("department", txtDepartement.getSelectedItemPosition());
        pfe.putStringSet("tel", telSet );
        pfe.apply();
    }
}