package com.gts.gts.Utility;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by john.nana on 8/10/2017.
 */

public class CustomUtility {
    public static void PROGRESSDIALOG(ProgressDialog pd, String Title){
         pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(Title);
        pd.show();
    }

    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }//end trim Message


}
