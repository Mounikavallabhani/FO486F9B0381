package com.dinosoftlabs.dnews.CodeClasses;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.BuildConfig;
import com.dinosoftlabs.dnews.social_app.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Variables {

    public static String BASEURL = "http://bringthings.com/API/news/";


    public static String WEB_VIEW_NEWS_LINK = BASEURL + "news.php?";
    public static int MY_SOCKET_TIMEOUT_MS = 30000;  // 30 Seconds
    public static int NUM_OF_POSTS_IN_HOME = 4;

    public static int height;
    public static int width;
    public static String LoginApi = BASEURL + "api/login";
    public static String SignUpApi = BASEURL + "api/registerUser";
    public static String SavedNewsAPI = BASEURL + "api/showFavouriteNews";
    public static String ShowsNewsAPI = BASEURL +  "api/showSectionNews";
    public static String News_detail= BASEURL + "api/showNewsDetail";
    public static String NewsCategories = BASEURL + "api/showCategories";
    public static String AddFavouriteNews = BASEURL + "api/AddFavouriteNews";
    public static String Slider_News_API = BASEURL + "api/showSliderNews";



    public static String Sections_News_API = BASEURL + "api/showSectionNews";

    public static String CategoriesWiseNews = BASEURL + "api/showCategoryNews";

    public static String SectionWiseAllNews = BASEURL + "api/showNewsAgainstSection";

    public static IResult mResultCallback = null;
    public static VolleyService mVolleyService;

    public static void Log_d_msg(Context contect, String Msg){
        Log.d(""+contect,""+Msg);
    }

    public static void toast_msg(Context context, String msg)
    {
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
        Log.d("on ",""+msg);
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

        // // TODO: 4/5/2019 Please Remove 
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

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /// End ALet Dialogue

    public static int volley_get_response_code(VolleyError get_error){
        return get_error.networkResponse.statusCode;
    }

    public static void increase_font_size(float title_text_size,float desc_text_size, TextView text_1,TextView text_2){


        title_text_size = title_text_size + 4;
        desc_text_size = desc_text_size + 5;
        text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_text_size);
        text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, desc_text_size);




    }


    public static void descrease_font_size(float title_text_size, float desc_text_size, TextView text_1,TextView text_2){

        title_text_size = title_text_size - 4;
        desc_text_size = desc_text_size - 5;
        text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, title_text_size);
        text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, desc_text_size);
    }


    public static void Like_News(int like, int news_id, ImageView like_button, ImageView dislike_button, ProgressDialog pDialog, Context context )
    {
        Variables.showpDialog(pDialog);
        initVolleyCallback_for_like_news(pDialog,like_button,dislike_button,context);

        mVolleyService = new VolleyService(mResultCallback, context);

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



                try{
                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("Favourite");
                    String fav  = Arr_1.getString("favourite");

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


    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public static String get_firebase_token(Context context){
        String token = FirebaseInstanceId.getInstance().getToken();
        Variables.toast_msg(context,""+token);
        return token;
    }
        ///////// Ads methods are here....
        public static InterstitialAd mInterstitialAd;
        public static InterstitialAd interstitial;
        public static void createNewIntAd (Context context)
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitial = new InterstitialAd(context);
            // Insert the Ad Unit ID
            interstitial.setAdUnitId(context.getString(R.string.interstacioys_ad_id));
            interstitial.loadAd(adRequest);
            // Prepare an Interstitial Ad Listener

            interstitial.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    // Call displayInterstitial() function
                    displayInterstitial();
                }
            });
        }
        public static void displayInterstitial() {
    // If Ads are loaded, show Interstitial else show nothing.
            if (interstitial.isLoaded()) {
                interstitial.show();
            }
        }
       ////// Banner ad Displaying Method
        //// Paasing paremeter of ADview on Calling, Then Banner ad Will display
        public static void Show_Banner_ad (AdView mAdView){
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        }



        public static void share_string(Context context,String text){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
                String shareMessage= "\n " +
                        "News from DNews  \n\n" +
                        "Here is news link.\n\n "+text + "\n" +

                        "Here is the Dcaller app link \n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }

        }



        public static String get_time_ago(String date_time){
            String ago_time = "";
            try
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date past = format.parse("2019-04-08 16:16:06");
                Date now = new Date();
                long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
                long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
                long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
                long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

                if(seconds<60)
                {
                    ago_time = seconds + " seconds ago";
                    System.out.println(seconds+" seconds ago");
                }
                else if(minutes<60)
                {
                    ago_time = minutes+" minutes ago";
                    System.out.println(minutes+" minutes ago");
                }
                else if(hours<24)
                {
                    ago_time = hours+" hours ago";
                    System.out.println(hours+" hours ago");
                }
                else
                {
                    ago_time = days+" days ago";
                    System.out.println(days+" days ago");
                }
            }
            catch (Exception j){
                j.printStackTrace();
            }

            return ago_time;
        }



}

