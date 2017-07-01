package com.example.jiehui.chatrobot;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jiehui on 6/30/17.
 */

public class PreferenceUtils {

    private static SharedPreferences sharedPreferences;

    public static void getSharedPreferences(Context context) {
        if(sharedPreferences == null){
            sharedPreferences = context.getApplicationContext().getSharedPreferences("config",context.getApplicationContext().MODE_PRIVATE);
        }
    }

    public static void putString(Context context,String key,String value){
        if(context.getApplicationContext() != null){
            getSharedPreferences(context.getApplicationContext());
        } else {
            getSharedPreferences(context);
        }
        sharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        getSharedPreferences(context.getApplicationContext());

        return sharedPreferences.getString(key,defValue);

    }

}
