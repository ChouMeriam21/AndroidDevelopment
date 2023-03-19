package fr.imt_atlantique.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class MainFragment extends Fragment {

    private EditText txtNom = null;
    private EditText txtPrenom = null;
    private TextView txtDateNaissance = null;

    private Button btnCalendar;
    private EditText txtVilleNaissance = null;
    private EditText txtTel = null;
    protected Button btnValidate;

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

    private Button btnPicture;

    private String imgPath = null;

    private Uri image = null;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    private View viewSnack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("mainFragmentoncreate","create");
        setHasOptionsMenu(true);
        utilisateur= this.getActivity().getSharedPreferences("utilisateur", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Defining layouts and views ________________________________________________________*/


        View root = inflater.inflate(R.layout.fragment_main, container, false);
        viewSnack = root.findViewById(R.id.constraintLayout);
        txtNom = (EditText) root.findViewById(R.id.txtNom);
        txtPrenom = (EditText) root.findViewById(R.id.txtPrenom);
        txtDateNaissance = (TextView) root.findViewById(R.id.txtDateNaissance);
        btnCalendar = (Button) root.findViewById(R.id.btnDate);
        txtVilleNaissance = (EditText) root.findViewById(R.id.txtVilleNaissance);
        btnValidate = (Button) root.findViewById(R.id.btnValidate);
        btnAjouter = (Button) root.findViewById(R.id.btnAjouter);
        txtTel = (EditText) root.findViewById(R.id.txtNumTel);
        layout = (LinearLayout)root.findViewById(R.id.linearLayout);
        txtDepartement = (Spinner) root.findViewById(R.id.txtDepartement);
        telSet = new HashSet<String>();
        btnPicture = (Button) root.findViewById(R.id.btnPicture);

        /* getting information from sharedpreferences and setting layouts ----------------------*/

        utilisateur= this.getActivity().getSharedPreferences("utilisateur", Context.MODE_PRIVATE);
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

        if (getArguments()!=null){
            if(getArguments().containsKey("date")){
                txtDateNaissance.setText(getArguments().getString("date"));
            }
        }




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
            LinearLayout subLayout = new LinearLayout(getActivity().getBaseContext());
            subLayout.setOrientation(LinearLayout.HORIZONTAL);
            Button delete = new Button(getActivity().getBaseContext());
            Button compose = new Button(getActivity().getBaseContext());
            delete.setText("SUPPRIMER");
            compose.setText("COMPOSER");
            TextView text = new TextView(getActivity().getBaseContext());
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

        /*buttons----------------------------------------------------------------@Override
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null) {
        int myInt = savedInstanceState.getInt("myInt");
        String myString = savedInstanceState.getString("myString");
        // Restore other saved data
    }
}--------------------------*/

        btnValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                int numberTel = layout.getChildCount();
                Log.i("viewSnack", String.valueOf(viewSnack));
                String textToShow;
                textToShow = txtNom.getText().toString()+"\n"+txtPrenom.getText().toString()+"\n"+txtDateNaissance.getText().toString()+"\n"+txtVilleNaissance.getText().toString()+"\n"+txtDepartement.getSelectedItem().toString()+"\n"+numberTel;
                Snackbar b1 = Snackbar.make(viewSnack, textToShow, Snackbar.LENGTH_SHORT);
                    /**public void onClick(View v) {
                        Log.i("viewSnack2", String.valueOf(viewSnack));
                        Snackbar b2 = Snackbar.make( viewSnack, "Dismissed Successfully", Snackbar.LENGTH_SHORT);
                        b2.show();
                    }

                });**/
                View snackbarView = b1.getView();
                TextView snackTextView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                snackTextView.setMaxLines(6);
                b1.show();
                ((MainActivity)getActivity()).onEditInfo(txtNom.getText().toString(), txtPrenom.getText().toString(), txtDateNaissance.getText().toString(), txtVilleNaissance.getText().toString(), txtDepartement.getSelectedItem().toString(), (String[]) telSet.toArray(new String[telSet.size()]));
            }
        });

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txtTelText = txtTel.getText().toString();
                telSet.add(txtTelText);
                LinearLayout subLayout = new LinearLayout(getActivity().getBaseContext());
                subLayout.setOrientation(LinearLayout.HORIZONTAL);
                Button delete = new Button(getActivity().getBaseContext());
                Button compose = new Button(getActivity().getBaseContext());
                delete.setText("SUPPRIMER");
                compose.setText("COMPOSER");
                TextView text = new TextView(getActivity().getBaseContext());
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
                DateFragment date_fragment= new DateFragment();
                date_fragment.ButtonValidateClicked=true;
                FragmentManager fmg = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fmg.beginTransaction();
                ft.replace(R.id.constraintLayout, date_fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        /*btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureIntent, 2);
            }
        });*/
        return root;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        getActivity();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getActivity().getBaseContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public interface OnValidateInterface{
        void onEditInfo(String txtNom, String txtPrenom, String txtDateNaissance,
                        String txtVilleNaissance,String txtDepartement, String[] telSet);
    }

    public void Save() {
        Log.i("utilisateur", String.valueOf(utilisateur));
        SharedPreferences.Editor pfe = utilisateur.edit();
        pfe.putString("nom", txtNom.getText().toString());
        pfe.putString("prenom", txtPrenom.getText().toString());
        pfe.putString("date", txtDateNaissance.getText().toString());
        pfe.putString("ville", txtVilleNaissance.getText().toString());
        pfe.putInt("department", txtDepartement.getSelectedItemPosition());
        pfe.putStringSet("tel", telSet );
        pfe.apply();
    }

    public void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
        Save();
    }

    public void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop method");
        Save();
    }

    public void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume method");
        /**getDateFromActivity();**/
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy method");

    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Lifecycle", "onDestroyView method");
        Save();
    }

    public void onDetach() {
        super.onDetach();
        Log.i("Lifecycle", "onDetach method");
    }

    public void resetAction() {
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

    public void invokeWiki() {
        Uri uri = Uri.parse("http://fr.wikipedia.org/?search="+txtVilleNaissance.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void shareVilleNaissance() {
        String text = txtVilleNaissance.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Partager votre ville de naissance avec :"));
        //startActivity(shareIntent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.resetAction){
            resetAction();
            Toast.makeText(getActivity(), "resetAction", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.invokeWiki){
            invokeWiki();
            Toast.makeText(getActivity(), "invokeWiki", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.share){
            shareVilleNaissance();
            Toast.makeText(getActivity(), "share ville de naissance", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}