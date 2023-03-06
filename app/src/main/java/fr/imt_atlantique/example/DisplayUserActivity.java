package fr.imt_atlantique.example;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayUserActivity extends AppCompatActivity {

    private TextView nom;
    private TextView prenom;
    private TextView ville;
    private TextView date;
    private TextView dep;

    private String[] tels;

    private LinearLayout layout;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        date = findViewById(R.id.date);
        dep = findViewById(R.id.dep);
        layout = findViewById(R.id.linearLayout);
        image = findViewById(R.id.image);

        Intent intent = getIntent();
        if (intent != null){
            User user = intent.getParcelableExtra("user");
            if (user != null){
                tels = user.getTel();
                // tu peux manipuler user !
                nom.setText(user.getNom());
                prenom.setText(user.getPrenom());
                ville.setText(user.getVilleNaissance());
                date.setText(user.getDateNaissance());
                dep.setText(user.getDepartement());
                Log.i("image", String.valueOf(intent.getStringExtra("image")));
                //image.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(intent.getStringExtra("image"))));
                if (intent.getStringExtra("image") != null) {
                    image.setImageURI(Uri.parse(intent.getStringExtra("image")));
                }

                for(int i = 0; i < tels.length ; i++) {
                    TextView text = new TextView(getBaseContext());
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setText(tels[i]);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
                    int newHeight = layout.getHeight() + dpToPx(60);
                    layoutParams.height = newHeight;
                    layout.setLayoutParams(layoutParams);
                    layout.addView(text);

                }
            }
        }

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
