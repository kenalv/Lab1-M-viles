package com.example.kenneth.lab1_mviles;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Arrieta on 28/2/2018.
 */

public class Usuario {
    private String name, profile
            , gender;
    private Uri imgProfile;

    public void setImg(Uri img){ this.imgProfile = img; }

    public Uri getImg(){ return imgProfile; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String address) {
        this.profile = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String type) {
        this.gender = type;
    }
}
