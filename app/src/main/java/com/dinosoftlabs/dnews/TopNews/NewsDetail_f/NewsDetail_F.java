package com.dinosoftlabs.dnews.TopNews.NewsDetail_f;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsDetail_F extends AppCompatActivity   implements Html.ImageGetter {

    ImageView IV;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private String TAG = "Saved";
    int News_id;
    TextView title,description;
    ImageView image_view, Share_News;
    ImageView like_button, dislike_button;
    private ProgressDialog pDialog;
    int like_dislike = 0;
    //int Dis_liked = 0;
    boolean is_wifi_availeable;
    int width, height;
    String webview_link;
    AdView mAdView;
    String title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
        Variables.Show_Banner_ad(mAdView);

        Share_News = findViewById(R.id.item_bottom_RL_download_IV_id);

        Display screensize = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        screensize.getSize(size);
        width = size.x;
        height = size.y;

        try{
            Variables.createNewIntAd(NewsDetail_F.this);

        }catch (Exception c){

        }

        //Variables.toast_msg(NewsDetail_F.this,"Heihj "+height+" wif "+width);

//        Dimension dimensions = driver.manage().window().getSize();
//        int screenWidth = dimensions.getWidth();
//        int screenHeight = dimensions.getHeight();


        Intent intent = getIntent();
        News_id = intent.getIntExtra("news_id",0); //if it's a string you stored.
        like_dislike = intent.getIntExtra("like_or_dislike",0);
        //Toast.makeText(this, ""+News_id, Toast.LENGTH_SHORT).show();
        title_text = intent.getStringExtra("title");
        webview_link = Variables.WEB_VIEW_NEWS_LINK + "id=" + News_id;

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());
        browser.loadUrl(webview_link);



        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        IV = (ImageView) findViewById(R.id.item_TB_IV_id);
        title = findViewById(R.id.item_inner_RL_TV1_id);
        description = findViewById(R.id.item_inner_RL_TV6_id);
        image_view = findViewById(R.id.item_inner_RL_IV_id);
        like_button = findViewById(R.id.item_bottom_RL_like_IV_id);

        title.setText(title_text);

        dislike_button = findViewById(R.id.item_bottom_RL_dislike_IV_id);
//        dislike_button.setVisibility(View.GONE);



        if(like_dislike==1){
            // If News like
            dislike_button.setVisibility(View.VISIBLE);
            like_button.setVisibility(View.GONE);
        }
        if(like_dislike==0)
        {
            // If News Dislike
            dislike_button.setVisibility(View.GONE);
            like_button.setVisibility(View.VISIBLE);
        }

        final View parent = (View) IV.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                IV.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , IV));
            }
        });

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Get News Description By Using API

     //   Get_Saved_News(News_id);


        /// Share button is here.

        Share_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Variables.share_string(NewsDetail_F.this,"" + webview_link);


            }
        });


    }


    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.mipmap.ic_dnews);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);

                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0,  d.getIntrinsicWidth(),  d.getIntrinsicHeight());

//                mDrawable.seth
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = description.getText();
                description.setText(t);
            }
        }
    }



    // Get Data From API
    public void Get_Saved_News(int news_id){
       // Variables.toast_msg(this,"Inside Method "+news_id);
        Variables.showpDialog(pDialog);

        initVolleyCallback();

        mVolleyService = new VolleyService(mResultCallback,NewsDetail_F.this);
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        String user_id_shared = SharedPrefrence.get_offline(NewsDetail_F.this,SharedPrefrence.shared_user_login_detail_key);

        if(user_id_shared != null){
            // If Values save in Locally
            // ==> If User Login

            int user_id = SharedPrefrence.get_user_id_from_json(NewsDetail_F.this);

            try {
                sendObj = new JSONObject("{'news_id': "+news_id+" , 'user_id': "+user_id+" }");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            // If User is not Logged in
            int user_id = 2;
            try {
                sendObj = new JSONObject("{'news_id': "+news_id+" , 'user_id': "+user_id+" }");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




        mVolleyService.postDataVolley("POSTCALL", Variables.News_detail, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
               try{
                   Variables.hidepDialog(pDialog);
                  // Variables.toast_msg(NewsDetail_F.this,"Response "+response);
                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("News");


                   //TV.setText(Html.fromHtml(text));

                    //title.setText("" + Html.fromHtml(Arr_1.getString("title")));

                   String des = Html.fromHtml((String) Arr_1.getString("description")).toString();

                   Html.fromHtml(Arr_1.getString("description"));

//  if (Arr_1.getString("description").length() > 100){
            des =  Arr_1.getString("description");

                   //getDrawable(Html.fromHtml(Html.fromHtml((String)  Arr_1.getString("description"))));

//        }
//        else {
//
//        }

                   title.setText("" + Html.fromHtml(Arr_1.getString("title")));
                   //description.setText(Html.fromHtml(des));
                    // Variables.toast_msg(NewsDetail_F.this,"" + Arr_1.getString("description"));
                   //description.setText(""+Html.fromHtml(Arr_1.getString("description")));

                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     /// description.setText(Html.fromHtml(des, Html.FROM_HTML_MODE_COMPACT));
                   } else {
                   //description.setText(Html.fromHtml(Html.fromHtml((String)  des).toString()));
                       //

//                       Spanned spanned = Html.fromHtml(des, NewsDetail_F.this, null);
//                       //TextView t2 = (TextView) findViewById(R.id.text2);
//                       description.setText(spanned);




                   }
               //    description.setText(Html.fromHtml(Html.fromHtml((String)  Arr_1.getString("description")).toString()));


//                   Spannable html;
//
//                   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                       html = (Spannable) Html.fromHtml(des, Html.FROM_HTML_MODE_LEGACY, NewsDetail_F.this, null);
//                   } else {
//                       html = (Spannable) Html.fromHtml(des, NewsDetail_F.this, null);
//                   }
//
//                   description.setText(html);



                   //   Variables.toast_msg(NewsDetail_F.this,""+Arr_1.getString("favourite"));


                   description.setText(Html.fromHtml(Html.fromHtml((String) des).toString(),
                           new Html.ImageGetter() {
                               @Override
                               public Drawable getDrawable(String source) {
                                   LevelListDrawable d = new LevelListDrawable();
                                   Drawable empty = getResources().getDrawable(R.mipmap.ic_dnews);
                                   d.addLevel(0, 0, empty);
                                   d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                                   new LoadImage().execute(source, d);

                                   return d;
                               }
                           }
                           , null));

                   description.setMovementMethod(LinkMovementMethod.getInstance());

                   if(Arr_1.getString("favourite").equals("1")){
                       // If news Like
                       like_button.setVisibility(View.GONE);
                       dislike_button.setVisibility(View.VISIBLE);

                   }else if(Arr_1.getString("favourite").equals("0")){
                       // If News Is not Like
                       like_button.setVisibility(View.VISIBLE);
                       dislike_button.setVisibility(View.GONE);
                   }


                   // String myHtml = String.valueOf(Html.fromHtml(Arr_1.getString("description")));
                   //description.setText(Html.fromHtml(Arr_1.getString("description"), new ImageGetter(), null));

              //    Spanned spanned = Html.fromHtml(Arr_1.getString("description"), NewsDetail_F.this, null);
                  // mTv = (TextView) findViewById(R.id.text);




                   //description.setText(Html.fromHtml(Arr_1.getString("description"), new ImageGetter(), null));


                    // Add Picasso
                    Picasso.get()
                            .load(Variables.BASEURL + Arr_1.getString("attachment"))
                            .placeholder(R.mipmap.ic_dnews)
                            .error(R.mipmap.ic_dnews)
                            .into(image_view);

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                     Variables.toast_msg(NewsDetail_F.this,"Please try again later. ");
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
               Variables.toast_msg(NewsDetail_F.this,"Error");

            }
        };
    }
    // ENd Data From API

    public void Like_News(int like,int news_id)
    {
        //Variables.toast_msg(this,"Inside like Method "+News_id);
        Variables.showpDialog(pDialog);
        initVolleyCallback_for_like_news();

        mVolleyService = new VolleyService(mResultCallback,NewsDetail_F.this);
        JSONObject sendObj = null;

        String user_json = SharedPrefrence.get_offline(NewsDetail_F.this,SharedPrefrence.shared_user_login_detail_key);
       int logged_in_user_id = SharedPrefrence.get_user_id_from_json(NewsDetail_F.this);

        if(user_json != null){
            // If user already Login

            try {
                sendObj = new JSONObject("{'news_id': "+news_id+"," +
                        " 'favourite' : "+like+"  , " +
                        " 'user_id' : "+logged_in_user_id+" }");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{
            // If User is not logged in

            try {
                sendObj = new JSONObject("{'news_id': "+news_id+"," +
                        " 'favourite' : "+like+"  , " +
                        " 'user_id' : 2 }");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        mVolleyService.postDataVolley("POSTCALL", Variables.AddFavouriteNews, sendObj);
    }


    public void like_button(View like)
    {

        // Getting User ID
        String user_id_shared = SharedPrefrence.get_offline(NewsDetail_F.this,SharedPrefrence.shared_user_login_detail_key);


        is_wifi_availeable=Variables.is_internet_avail(this);
        if(is_wifi_availeable==true)
        {
            if(user_id_shared != null){
                // If Wifi Available & User is already logged in
                Like_News(1, News_id);
            }else{
                 // If wifi available and user is not Logged in
                // If Wifi Available
                String title = getResources().getString(R.string.info);
                String msg = getResources().getString(R.string.not_login);
                try{
                    Variables.alert_dialogue(this,getResources().getString(R.string.info),
                            getResources().getString(R.string.not_login));
                }catch (Exception t){

                }
            }


        }else {
            // => If wifi is not Available
            String title= getResources().getString(R.string.info);
            String msg= getResources().getString(R.string.wifi_connected_info);
            try{
                Variables.alert_dialogue(this,""+title,""+msg);
            }catch (Exception t){

            }

        }

    }

    public void DisLike_News(View V)
    {

        String user_id_shared = SharedPrefrence.get_offline(NewsDetail_F.this,SharedPrefrence.shared_user_login_detail_key);


        is_wifi_availeable=Variables.is_internet_avail(this);
        if(is_wifi_availeable==true)
        {
            if(user_id_shared != null){
                // If user is Already Logged in
                // If Wifi Available
                Like_News(0, News_id);
            }else{
                // If user is not Logged in
                String title = getResources().getString(R.string.info);
                String msg = getResources().getString(R.string.not_login);
                try{
                    Variables.alert_dialogue(this,"" + title,"" + msg);
                }catch (Exception t){

                }

            }



        }else {
            // => If wifi is not Available
            String title=getResources().getResourceName(R.string.info);
            String msg=getResources().getResourceName(R.string.wifi_connected_info);
            try{
                Variables.alert_dialogue(this,""+title,""+msg);
            }catch (Exception t){

            }

        }




    }

    void initVolleyCallback_for_like_news(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                try{
                    JSONObject Arr= response.getJSONObject("msg");
                    JSONObject Arr_1 = Arr.getJSONObject("Favourite");
                    String fav  = Arr_1.getString("favourite");
                    if(fav.equals("1")){
                        Variables.toast_msg(NewsDetail_F.this,"Favourite Added.");
                        like_button.setVisibility(View.GONE);
                        dislike_button.setVisibility(View.VISIBLE);
                    }else if(fav.equals("0"))
                       {
                           // Dislike
                           Variables.toast_msg(NewsDetail_F.this,"Dislike.");
                           like_button.setVisibility(View.VISIBLE);
                           dislike_button.setVisibility(View.GONE);
                       }

                }catch (Exception v){
                    Variables.hidepDialog(pDialog);
                    Variables.toast_msg(NewsDetail_F.this,"Please try again later. "+v.toString());
                }

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                try{
                    int status_code=Variables.volley_get_response_code(error);
                    Variables.Log_d_msg(NewsDetail_F.this,"Volley requester " + requestType);
                    Variables.toast_msg(NewsDetail_F.this,"Like Error "+error.toString()+" Code "+status_code);
                }catch (Exception v){

                }
                // Get Response HTTP code
            }
        };
    }

    /// ===> Method for increaing the font size
    public void increase_font_size(View size){

        float px = title.getTextSize();
        float desc_px = description.getTextSize();

        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        float desc_sp = desc_px / getResources().getDisplayMetrics().scaledDensity;


        Variables.increase_font_size(sp,desc_sp,title,description);



    }


    public void decrease_font_size(View decrease){


        float px = title.getTextSize();
        float desc_px = description.getTextSize();

        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        float desc_sp = desc_px / getResources().getDisplayMetrics().scaledDensity;

         Variables.descrease_font_size(sp,desc_sp,title,description);


    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            pDialog.show();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pDialog.hide();

        }

    }


}


