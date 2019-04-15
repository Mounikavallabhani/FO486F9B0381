package com.dinosoftlabs.dnews.Category.Category_Wise_News;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.TopNews.DataModels.News_Section_Model;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.squareup.picasso.Picasso;

import java.util.List;

public class More_Details_Adapter extends RecyclerView.Adapter<More_Details_Adapter.ViewHolder> {

    private ProgressDialog pDialog;
    public More_Details_Adapter.onClick itemclick;
    private List<News_Section_Model> News_list;
    Context context;
    public interface onClick{
        void clickAction(int pos);
    }
    public More_Details_Adapter(More_Details_Adapter.onClick itemclick, List<News_Section_Model> Category_list, Context context) {
        this.itemclick = itemclick;
        this.News_list=Category_list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.more_details_item,null);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        return new ViewHolder(v);
    }


    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imageView.setClipToOutline(true);
        }
        final News_Section_Model News = News_list.get(i);

        Picasso.get()
                .load( Variables.BASEURL + News.getImage_url()).fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);

        viewHolder.ago_time.setText("" + News.getAgo_time());

        viewHolder.Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ==> Open a new Activity of News Detail

                //// Check Network Availability

                boolean is_wifi_availeable = Variables.is_internet_avail(context);

                if(is_wifi_availeable == true){
                    /// If wifi Available

                    Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                    myIntent.putExtra("news_id",  News.getNews_id());
                    myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
                    myIntent.putExtra("title", News.getTitle());
                    view.getContext().startActivity(myIntent);

                }else{

                    /// If wifi is not available
                    Variables.alert_dialogue(context,
                            "" + context.getResources().getString(R.string.info),
                            "" + context.getResources().getString(R.string.wifi_connected_info)
                    );

                }




            }
        });




        if(News.getIs_like_dis_like()==0){
            // Dislike Show
            viewHolder.Dis_like.setVisibility(View.VISIBLE);
        }else if(News.getIs_like_dis_like()==1){
            // Show Filled heart
            viewHolder.like.setVisibility(View.VISIBLE);
        }

        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ========> Code for Dislike
                String webview_link = Variables.WEB_VIEW_NEWS_LINK + "id=" + News.getNews_id();
                Variables.share_string(context,"" + webview_link);




            }
        });



        viewHolder.Dis_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ========> Code for Dislike

                String user_id_shared = SharedPrefrence.get_offline(context,SharedPrefrence.shared_user_login_detail_key);

                if(user_id_shared != null){
                    // If Wifi Available & User is already logged in
                    Variables.Like_News(0, News.getNews_id(),viewHolder.like,viewHolder.Dis_like,pDialog,context);

                }else{
                    // If wifi available and user is not Logged in
                    // If Wifi Available
                    String title = context.getResources().getString(R.string.info);
                    String msg = context.getResources().getString(R.string.not_login);
                    try{
                        Variables.alert_dialogue(context,context.getResources().getString(R.string.info),
                                context.getResources().getString(R.string.not_login));
                    }catch (Exception t){

                    }
                }





            }
        });


        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // ========> Code for like

                // Getting User ID
                String user_id_shared = SharedPrefrence.get_offline(context,SharedPrefrence.shared_user_login_detail_key);

                if(user_id_shared != null){
                    // If Wifi Available & User is already logged in
                    Variables.Like_News(1, News.getNews_id(),viewHolder.like,viewHolder.Dis_like,pDialog,context);
                }else{
                    // If wifi available and user is not Logged in
                    // If Wifi Available
                    String title = context.getResources().getString(R.string.info);
                    String msg = context.getResources().getString(R.string.not_login);
                    try{
                        Variables.alert_dialogue(context,context.getResources().getString(R.string.info),
                                context.getResources().getString(R.string.not_login));
                    }catch (Exception t){

                    }
                }






            }
        });




        viewHolder.textView.setText(Html.fromHtml(News.getTitle()));

    }

    @Override
    public int getItemCount() {
        return News_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, ago_time;
        RelativeLayout Main;
        ImageView like, Dis_like, share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.more_details_item_IV_id);
            textView = (TextView) itemView.findViewById(R.id.more_details_item_TV_id);
            Main = itemView.findViewById(R.id.main);
            like = itemView.findViewById(R.id.like);
            Dis_like = itemView.findViewById(R.id.dis_like);
            share = itemView.findViewById(R.id.more_details_item_bottom_RL_download_IV_id);
            ago_time = itemView.findViewById(R.id.more_details_item_TV2_id);
        }
    }
}
