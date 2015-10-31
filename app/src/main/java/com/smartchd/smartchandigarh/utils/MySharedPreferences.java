package com.smartchd.smartchandigarh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by raghav on 31/10/15.
 */
public class MySharedPreferences {

    private SharedPreferences sharedPreferences;

    public static final String SHARED_PREFERENCES = "scprefs";
    public static final String USER_FIRST_NAME = "name";
    public static final String USER_LAST_NAME = "name";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";
    public static final String USER_PROFILE_PIC = "profile_pic";

    public MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getFirstName() {
        return sharedPreferences.getString(USER_FIRST_NAME, null);
    }

    public String getLastName() {
        return sharedPreferences.getString(USER_LAST_NAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public String getPhone() {
        return sharedPreferences.getString(USER_PHONE, null);
    }

    public String getProfileUri(){
        return sharedPreferences.getString(USER_PROFILE_PIC, null);
    }

    public boolean setFirstName(String firstName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FIRST_NAME, firstName);
        return editor.commit();
    }

    public boolean setLastName(String lastName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_LAST_NAME, lastName);
        return editor.commit();
    }

    public boolean setPhone(String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHONE, phone);
        return editor.commit();
    }

    public boolean setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        return editor.commit();
    }

    public boolean setProfilePic(Uri profilePic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PROFILE_PIC, profilePic.toString());
        return editor.commit();
    }
}
