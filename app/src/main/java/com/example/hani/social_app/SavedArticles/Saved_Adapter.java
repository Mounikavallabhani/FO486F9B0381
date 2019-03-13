package com.example.hani.social_app.SavedArticles;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.Discover_Adapter_two;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Saved_Adapter extends RecyclerView.Adapter<Saved_Adapter.ViewHolder> {



    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
                    ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
                    ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};

    String[] text = {"Explore the most popular festivals around the world",
                    "Discover the best images on Unsplash in 2017",
                    "The most popular places to travel in December",
                    "The latest art exibition opened last week",
                    "Discover the best images on Unsplash in 2017",
                    "The most popular places to travel in December",
                    "The latest art exibition opened last week",
                    "Discover the best images on Unsplash in 2017",
                    "The most popular places to travel in December",
                    "The latest art exibition opened last week",
                    "Discover the best images on Unsplash in 2017",
                    "The most popular places to travel in December"};

    Integer[] text1 = {R.color.blue,R.color.colorAccent,R.color.black,
                    R.color.colorPrimary,R.color.colorAccent,R.color.black,
                    R.color.colorAccent,R.color.blue,R.color.colorPrimary,
                    R.color.blue,R.color.blue,R.color.blue};

    String[] text2 = {"FESTIVAL",
            "TECHNOLOGY",
            "ARTS",
            "DESIGN",
            "ACTIVITY",
            "FASHION",
            "BEAUTY",
            "COLOR",
            "SCIENCE",
            "MAKEUP",
            "RECEIPES",
            "OBJECTS"};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item_rl,null);
        return new ViewHolder(v);
    }


    public Saved_Adapter.onClick itemclick;
    private List<NewsDataMode> News_List_adapter;
    public interface onClick{
        void clickAction(int pos);
    }

    public Saved_Adapter(Saved_Adapter.onClick itemclick, List<NewsDataMode> news_List) {
        this.itemclick = itemclick;
        this.News_List_adapter=news_List;
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
        NewsDataMode News = News_List_adapter.get(i);

        Picasso.get()
                .load(News.getImage_url())
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);

//        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(News.getTitle());
        viewHolder.textView1.setText(News.getCategory());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
       // view.setBackgroundColor(color);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_recyclerview_2_IV_id);
            textView = (TextView) itemView.findViewById(R.id.item_recyclerview_2_TV_id);
            textView1 = (TextView) itemView.findViewById(R.id.item_recyclerview_2_TV1_id);
            RKL = (RoundKornerRelativeLayout) itemView.findViewById(R.id.item_recyclerview_2_RKR1_id);
        }
    }
}
