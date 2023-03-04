package fr.imt_atlantique.example;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Set;

public class User implements Parcelable {

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String villeNaissance;
    private String departement;

    private String[] tel;


    public User(String nom, String prenom, String dateNaissance, String villeNaissance, String departement, String[] tel) {

        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.villeNaissance = villeNaissance;
        this.departement = departement;
        this.tel = tel;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public String getVilleNaissance() {
        return this.villeNaissance;
    }

    public String getDepartement() {
        return this.departement;
    }

    public String[] getTel() {
        return this.tel;
    }
    protected User(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = in.readString();
        villeNaissance = in.readString();
        departement = in.readString();
        tel = in.createStringArray();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(dateNaissance);
        dest.writeString(villeNaissance);
        dest.writeString(departement);
        dest.writeStringArray(tel);
    }
}
