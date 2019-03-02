package com.example.hani.social_app.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrence {

    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;

    public static void init_share(Context context){
        pref = context.getSharedPreferences("Dnews", 0); // 0 - for private mode
        editor = pref.edit();
    }

    public static void save_response_share(Context context,String json)
    {
        init_share(context);
        editor.putString("name", json); // Storing string
        editor.commit();
    }

    public static void get_offline(String key){
        
        pref.getString(key, null);

    }


}
