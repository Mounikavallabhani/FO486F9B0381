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
import android.widget.ImageView;
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
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String ShowsNewsAPI = BASEURL +  "api/showSectionNews";
    public static String News_detail= BASEURL + "api/showNewsDetail";
    public static String NewsCategories = BASEURL + "api/showCategories";
    public static String AddFavouriteNews = BASEURL + "api/AddFavouriteNews";

    public static String Slider_News_API = BASEURL + "api/showSliderNews";
   // http://bringthings.com/news/
    public static String Sections_News_API = BASEURL + "api/showSectionNews";

    public static String CategoriesWiseNews = BASEURL + "api/showCategoryNews";

//    http://bringthings.com/news/
//
    public static String SectionWiseAllNews = BASEURL + "api/showNewsAgainstSection";

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
       avail = true;
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

    public static void increase_font_size(float title_text_size,float desc_text_size, TextView text_1,TextView text_2){
//        if(num_clicks==1){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        }else if(num_clicks==2){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
//        }else if(num_clicks==4) {
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
//        }else if(num_clicks==4){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
//        }else{
//
//        }

//        int title_size = 28;
//        int desc_size = 19;
//        Log.d("Font Size",""+title_text_size);
//        title_text_size = title_text_size + 4;
//        desc_text_size = desc_text_size + 5;
//        text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_text_size);
//        text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, desc_text_size);

        title_text_size = title_text_size + 4;
        desc_text_size = desc_text_size + 5;
        text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_text_size);
        text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, desc_text_size);




    }


    public static void descrease_font_size(float title_text_size, float desc_text_size, TextView text_1,TextView text_2){
//        if(num_clicks==1){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        }else if(num_clicks==2){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
//        }else if(num_clicks==4) {
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
//        }else if(num_clicks==4){
//            text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
//            text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
//        }

//        int title_size = 28;
//        int desc_size = 19;

        title_text_size = title_text_size - 4;
        desc_text_size = desc_text_size - 5;
        text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_text_size);
        text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, desc_text_size);
    }


    public static void Like_News(int like, int news_id, ImageView like_button, ImageView dislike_button, ProgressDialog pDialog, Context context )
    {
        //Variables.toast_msg(this,"Inside like Method "+News_id);
        Variables.showpDialog(pDialog);
        initVolleyCallback_for_like_news(pDialog,like_button,dislike_button,context);

        mVolleyService = new VolleyService(mResultCallback, context);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'news_id': "+news_id+"," +
                    " 'favourite' : "+like+"  , " +
                    " 'user_id' : 2 }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.AddFavouriteNews, sendObj);
    }


    static void initVolleyCallback_for_like_news(final ProgressDialog pDialog, final ImageView like_button, final ImageView dislike_button, final Context context){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                Variables.Log_d_msg(context,""+"Volley requester " + requestType);
                Variables.Log_d_msg(context,"Volley JSON post" + response.toString());


                try{
                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("Favourite");
                    String fav  = Arr_1.getString("favourite");
                    //  title.setText(""+Arr_1.getString("title"));
                    //description.setText(""+Arr_1.getString("description"));
                    // Add Picasso
//                    Picasso.get()
//                            .load(Arr_1.getString("attachment"))
//                            .placeholder(R.mipmap.ic_dnews)
//                            .error(R.mipmap.ic_dnews)
//                            .into(image_view);
                    if(fav.equals("1")){
                        Variables.toast_msg(context,"Favourite Added.");
                        like_button.setVisibility(View.GONE);
                        dislike_button.setVisibility(View.VISIBLE);
                    }else if(fav.equals("0"))
                    {
                        // Dislike
                        Variables.toast_msg(context,"Dislike.");
                        like_button.setVisibility(View.VISIBLE);
                        dislike_button.setVisibility(View.GONE);
                    }

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(context,"Please try again later. "+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                try{
                    int status_code=Variables.volley_get_response_code(error);
                    Variables.Log_d_msg(context,"Volley requester " + requestType);
                    Variables.toast_msg(context,"Like Error "+error.toString()+" Code "+status_code);
                }catch (Exception v){

                }
                // Get Response HTTP code
            }
        };
    }



    /// ==> VEmail Validation
    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }



    // ENd Email Validation

// RTL detecting

    public static boolean isRTL() {
//        return isRTL(Locale.getDefault());

        return isRTL(Locale.CHINESE);

    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }


}

