package com.example.hani.social_app.CodeClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hani.social_app.SavedArticles.Saved;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Variables {
    public static String BASEURL = "http://bringthings.com/news/";
    public static int MY_SOCKET_TIMEOUT_MS = 30000;  // 30 Seconds
    public static int height;
    public static int width;
    public static String LoginApi = "http://bringthings.com/news/api/login";
    public static String SignUpApi = "http://bringthings.com/news/api/registerUser";
    public static String SavedNewsAPI = "http://bringthings.com/news/api/showFavouriteNews";
    public static String ShowsNewsAPI =  "http://bringthings.com/news/api/showNews";
    public static String News_detail= "http://bringthings.com/news/api/showNewsDetail";
    public static String NewsCategories = "http://bringthings.com/news/api/showCategories";


    public static IResult mResultCallback = null;
    public static VolleyService mVolleyService;

    public static void Log_d_msg(Context contect, String Msg){
        Log.d(""+contect,""+Msg);
    }

    public static void toast_msg(Context context, String msg)
    {
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
    }

    public static void hide(View v,View text)
    {

        v.setVisibility(View.GONE);
        text.setVisibility(View.GONE);

    }

    public static void Show(View v,View text)
    {
        v.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
    }

    public static  void showpDialog(ProgressDialog pDialog) {
//        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hidepDialog(ProgressDialog pDialog) {
        //if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public static boolean is_internet_avail(Context context)
    {
    boolean avail = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

//For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);

        if (!is3g && !isWifi)
        {
     //       Toast.makeText(context,"Please make sure your Network Connection is ON ",Toast.LENGTH_LONG).show();
        }
        else
        {
             avail = true;
        }

        // New Code

        return avail;

    }

    private boolean isNetworkAvailable(Context contect) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) contect.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

