package com.dinosoftlabs.dnews.SavedArticles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.dinosoftlabs.dnews.TopNews.DataModels.NewsDataMode;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Saved_Adapter extends RecyclerView.Adapter<Saved_Adapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item_rl,null);
        return new ViewHolder(v);
    }


    public Saved_Adapter.onClick itemclick;
    private List<NewsDataMode> News_List_adapter;
    Context context;
    public interface onClick{
        void clickAction(int pos);
    }

    public Saved_Adapter(Saved_Adapter.onClick itemclick, List<NewsDataMode> news_List, Context context) {
        this.itemclick = itemclick;
        this.News_List_adapter=news_List;
        this.context = context;
    }

    public void updateList(ArrayList<NewsDataMode> list){
        News_List_adapter = list;
        notifyDataSetChanged();
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imageView.setClipToOutline(true);
        }
        final NewsDataMode News = News_List_adapter.get(i);


        Picasso.get()
                .load(Variables.BASEURL + News.getImage_url())
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);


        viewHolder.Main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        boolean is_wifi_availeable = Variables.is_internet_avail(context);

                        if(is_wifi_availeable == true) {
                            /// If wifi Available
                            Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                            myIntent.putExtra("news_id",  News.getId());
                            myIntent.putExtra("like_or_dislike", News.getLike_dislile());
                            //Optional parameters
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


        viewHolder.textView.setText(Html.fromHtml(News.getTitle()));

        viewHolder.textView1.setText(News.getCategory());

     /// ==> Set Random Colors of Titles

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        viewHolder.RKL.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return News_List_adapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView,textView1;
        RoundKornerRelativeLayout RKL;
        RelativeLayout Main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_recyclerview_2_IV_id);
            textView = (TextView) itemView.findViewById(R.id.item_recyclerview_2_TV_id);
            textView1 = (TextView) itemView.findViewById(R.id.item_recyclerview_2_TV1_id);
            RKL = (RoundKornerRelativeLayout) itemView.findViewById(R.id.item_recyclerview_2_RKR1_id);
            Main = itemView.findViewById(R.id.main);
        }
    }
}
