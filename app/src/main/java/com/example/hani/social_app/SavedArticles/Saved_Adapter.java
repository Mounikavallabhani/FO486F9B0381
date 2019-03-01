package com.example.hani.social_app.SavedArticles;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.R;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;

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


    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imageView.setClipToOutline(true);
        }
        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(text[i]);
        viewHolder.textView1.setText(text2[i]);
        viewHolder.RKL.setBackgroundResource(text1[i]);

    }

    @Override
    public int getItemCount() {
        return img.length;
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
