package com.gts.gts.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by john.nana on 8/6/2017.
 */

public class PrefManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "GTS";

    // All Shared Preferences Keys

    private static final String KEY_PHONE = "mobile_number";
    private static final String KEY_NAME = "name";
    private static final String KEY_DIAL_CODE = "dial_code";
    private static final String KEY_ROLE = "role";
    private static final String KEY_EID = "eid";
    private static final String KEY_LOCATION = "location";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // generate getter and setter methods
    public  String getName() {
        return pref.getString(KEY_NAME, "");
    }

    public void setName(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }


    public String getPhone() {
        return pref.getString(KEY_PHONE, "");
    }

    public void setPhone(String phone) {
        editor.putString(KEY_PHONE, phone);
        editor.commit();
    }

    public  String getDialCode() {
        return pref.getString(KEY_DIAL_CODE, "");
    }

    public  void setDialCode(String dial_code) {
        editor.putString(KEY_DIAL_CODE, dial_code);
        editor.commit();
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, "");
    }

    public void setRole(String role) {
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    public String getEid() {
        return pref.getString(KEY_EID, "");
    }

    public  void setEid(String eid) {
        editor.putString(KEY_EID, eid);
        editor.commit();
    }

    public String getLocation() {
        return pref.getString(KEY_LOCATION, "");
    }

    public void setLocation(String location) {
        editor.putString(KEY_LOCATION, location);
        editor.commit();
    }

    public boolean getIsLoggedIn (){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn (boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

}
