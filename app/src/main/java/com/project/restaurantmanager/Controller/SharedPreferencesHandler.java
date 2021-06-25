package com.project.restaurantmanager.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

public class SharedPreferencesHandler {

    public static SharedPreferences pref ;
    private SharedPreferences.Editor Editor;

    @SuppressLint("CommitPrefEdits")

    public SharedPreferencesHandler(Context c) {
        this.pref = c.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        Editor = pref.edit();

    }

    public Boolean getFlag() {
        return pref.getBoolean("FLAG", false);
    }

    public void setFlag(Boolean flag) {
        Editor.putBoolean("FLAG",flag);
        Editor.commit();
    }

    public String getName() {
        return pref.getString("NAME", "");
    }

    public void setrName(String name) {
        Editor.putString("NAME",name);
        Editor.commit();
    }

    public String getEmail() {
        return pref.getString("EMAIL", "");
    }

    public void setEmail(String email) {
        Editor.putString("EMAIL",email);
        Editor.commit();
    }

    public String getUsername() {
        return pref.getString("USERNAME", "");
    }

    public void setUsername(String username) {
        Editor.putString("USERNAME",username);
        Editor.commit();
    }

    public String getAddress() {
        return pref.getString("ADDRESS", "");
    }

    public void setAddress(String email) {
        Editor.putString("ADDRESS",email);
        Editor.commit();
    }

    public String getMobile() {
        return pref.getString("MOBILE", "");
    }

    public void setMobile(String email) {
        Editor.putString("MOBILE",email);
        Editor.commit();
    }

    public String getPost() {
        return pref.getString("POST", "");
    }

    public void setPost(String post) {
        Editor.putString("POST",post);
        Editor.commit();
    }

    public String getId() {
        return pref.getString("ID", "");
    }

    public void setId(String id) {
        Editor.putString("ID",id);
        Editor.commit();
    }

    public String getRid() {
        return pref.getString("RID", "");
    }

    public void setRid(String id) {
        Editor.putString("RID",id);
        Editor.commit();
    }

    public String getToken() {
        return pref.getString("TOKEN", "");
    }

    public void setToken(String token) {
        Editor.putString("TOKEN",token);
        Editor.commit();
    }
}
