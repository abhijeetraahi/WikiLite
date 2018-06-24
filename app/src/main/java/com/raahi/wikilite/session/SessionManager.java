package com.raahi.wikilite.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Raahi on 24-06-2018.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "WikiLite";
    private static final String LAST_DETAILS = "LastDetails";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */
    public void saveLatestDetails(String details){
        editor.putString(LAST_DETAILS, details);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public String getLatestDetails(){
        return pref.getString(LAST_DETAILS,""); // return last saved data
    }
}
