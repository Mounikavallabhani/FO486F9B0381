package com.example.hani.social_app.CodeClasses;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.hani.social_app.SavedArticles.Saved;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import okhttp3.internal.http2.ErrorCode;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Variables {
    public static String BASEURL = "http://bringthings.com/news/";
    public static int MY_SOCKET_TIMEOUT_MS = 30000;  // 30 Seconds
    public static int height;
    public static int width;
    public static String LoginApi = BASEURL + "/api/login";
    public static String SignUpApi = BASEURL + "api/registerUser";
    public static String SavedNewsAPI = BASEURL + "api/showFavouriteNews";
    public static String ShowsNewsAPI = BASEURL +  "api/showNews";
    public static String News_detail= BASEURL + "api/showNewsDetail";
    public static String NewsCategories = BASEURL + "api/showCategories";
    public static String AddFavouriteNews = BASEURL + "api/AddFavouriteNews";

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
        // TODO: 3/5/2019
//        avail = true;
        return avail;

    }

    private boolean isNetworkAvailable(Context contect) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) contect.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /// Alert Dialogue
    public static void alert_dialogue(final Context context, String title, String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(""+title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /// End ALet Dialogue

    public static int volley_get_response_code(VolleyError get_error){
        return get_error.networkResponse.statusCode;
    }

    public static void increase_font_size(int num_clicks, TextView text_1,TextView text_2){
        if(num_clicks==1){
            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        }else if(num_clicks==2){
            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        }else if(num_clicks==4) {
            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
        }else if(num_clicks==4){
            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
        }


    }





}

