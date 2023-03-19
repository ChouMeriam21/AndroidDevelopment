package fr.imt_atlantique.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Set;


public class DisplayFragment extends Fragment {


    private TextView nom;
    private TextView prenom;
    private TextView ville;
    private TextView date;
    private TextView dep;

    private String[] tels;

    private LinearLayout layout;

    private ImageView image;
    private SharedPreferences utilisateur;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display, container, false);
        // Inflate the layout for this fragment
        nom = root.findViewById(R.id.nom);
        prenom = root.findViewById(R.id.prenom);
        ville = root.findViewById(R.id.ville);
        date = root.findViewById(R.id.date);
        dep = root.findViewById(R.id.dep);
        layout = root.findViewById(R.id.linearLayout);
        image = root.findViewById(R.id.image);

        if(getArguments().containsKey("nom")){
            nom.setText(getArguments().getString("nom"));
        }
        if(getArguments().containsKey("prenom")){
            prenom.setText(getArguments().getString("prenom"));
        }
        if(getArguments().containsKey("villeNaissance")){
            ville.setText(getArguments().getString("villeNaissance"));
        }
        if(getArguments().containsKey("dateNaissance")){
            date.setText(getArguments().getString("dateNaissance"));
        }
        if(getArguments().containsKey("departement")){
            dep.setText(getArguments().getString("departement"));
        }
        if(getArguments().containsKey("tel")){
            tels = getArguments().getStringArray("tel");
        }
        for(int i = 0; i < tels.length ; i++) {
            TextView text = new TextView(this.getActivity().getBaseContext());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text.setText(tels[i]);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
            int newHeight = layout.getHeight() + dpToPx(60);
            layoutParams.height = newHeight;
            layout.setLayoutParams(layoutParams);
            layout.addView(text);

        }

        return root;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getActivity().getBaseContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}