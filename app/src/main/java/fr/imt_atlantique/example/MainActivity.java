package fr.imt_atlantique.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class MainActivity extends AppCompatActivity implements MainFragment.OnValidateInterface, DateFragment.OnDatePicker{


    private SharedPreferences preferences;
    private String fragment;
    private String nom;
    private String prenom;
    private String ville;
    private String date;
    private String dep;
    private String[] telSet;

    private String currentFragmentTag;
    private static final String FRAGMENT_TAG_KEY = "fragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Lifecycle", "onCreate method");

        preferences = this.getSharedPreferences("utilisateur", Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString(FRAGMENT_TAG_KEY);
            Log.i("currentFragmentTag", "ok");
        } else {
            // If there is no saved instance state, set the default fragment
            currentFragmentTag = "defaultFragmentTag";
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.constraintLayout, new MainFragment(), currentFragmentTag)
                    .commit();
        }
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", String.valueOf(resultCode == Activity.RESULT_OK));
        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                String date = data.getStringExtra("date");
                Log.i("date", String.valueOf(date));
                txtDateNaissance.setText(date);
            }
            else {
                txtDateNaissance.setText(DateUtilisateur);
            }
        }
        if(requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                image = data.getData();
            }
        }
    }*/


    /**public void getDateFromActivity() {
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




    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart method");

    }*/

    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
        //Save();
    }

    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop method");
        //Save();
    }

    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume method");
        /**getDateFromActivity();**/

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy method");
        //Save();
    }



    @Override
    public void onEditInfo(String txtNom, String txtPrenom, String txtDateNaissance, String txtVilleNaissance, String txtDepartement, String[] telSet) {
        DisplayFragment display_fragment= new DisplayFragment();
        FragmentManager fmg = getSupportFragmentManager();
        FragmentTransaction ft = fmg.beginTransaction();
        Bundle arg = new Bundle();
        arg.putString("nom", txtNom);
        arg.putString("prenom", txtPrenom);
        arg.putString("dateNaissance", txtDateNaissance);
        arg.putString("villeNaissance", txtVilleNaissance);
        arg.putString("departement", txtDepartement);
        arg.putStringArray("tel",telSet);
        display_fragment.setArguments(arg);
        ft.replace(R.id.constraintLayout, display_fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the tag of the currently visible fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.constraintLayout);
        if (currentFragment != null) {
            currentFragmentTag = currentFragment.getTag();
            outState.putString(FRAGMENT_TAG_KEY, currentFragmentTag);
        }
    }

    @Override
    public void onDateUser(String date) {
        MainFragment main_fragment= new MainFragment();
        FragmentManager fmg = getSupportFragmentManager();
        FragmentTransaction ft = fmg.beginTransaction();
        Bundle arg = new Bundle();
        arg.putString("date", date);
        main_fragment.setArguments(arg);
        ft.replace(R.id.constraintLayout, main_fragment);
        ft.commit();
    }

}