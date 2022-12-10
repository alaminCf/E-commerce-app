package com.softaloy.softe_commerce.models;

import android.widget.EditText;

public class UserModels {

   private String email,  name, password, profile_image;

    public UserModels() {
    }

    public UserModels(String name, String email, String password, String profile_image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.profile_image = profile_image;
    }

    public UserModels(EditText name, EditText email, EditText password) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}



