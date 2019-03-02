package com.example.hani.social_app.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hani.social_app.CodeClasses.Variables;

import org.json.JSONObject;

public class SharedPrefrence {

    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;
    public static String shared_category_key = "category";
    public static String shared_saved_news_key = "saved";
    public static String shared_discover_news_key = "discover";

    public static void init_share(Context context){
        pref = context.getSharedPreferences("Dnews", 0); // 0 - for private mode
        editor = pref.edit();
    }

    public static void save_response_share(Context context,String value,String data_key)
    {
        init_share(context);
        editor.putString(data_key, value); // Storing string
        editor.commit();
    }

    public static String get_offline(Context context, String key){
        init_share(context);
        pref.getString(key, null);
       // Variables.toast_msg(context,"Value "+pref.getString(key, null));
        return pref.getString(key, null);

    }


}
