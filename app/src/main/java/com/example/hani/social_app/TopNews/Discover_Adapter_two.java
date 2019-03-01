package com.example.hani.social_app.TopNews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hani.social_app.R;

public class Discover_Adapter_two extends RecyclerView.Adapter<Discover_Adapter_two.ViewHolder> {

    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
            ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
            ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};

    String[] text = {"Explore the most popular festivals around the world",
            "Discover the best images 2017",
            "The most popular places to travel in December",
            "The latest art exibition opened",
            "Discover best images Unsplash in 2017",
            "The most popular places to travel in December",
            "The latest art exibition opened last week",
            "Discover best images on Unsplash in 2017",
            "The most popular places to travel in December",
            "The latest exibition opened last week",
            "Discover the best images on Unsplash in 2017",
            "The most popular places to travel in December"};



    String[] text1 = {"FESTIVALS",
            "ENVIORNMENT",
            "ARTS",
            "FASHION",
            "BEAUTY",
            "TRAVEL",
            "ENVIORNMENT",
            "ARTS",
            "FASHION",
            "BEAUTY",
            "TRAVEL",
            "FASHION"};

    Integer[] text2 = {R.color.blue,R.color.colorAccent,R.color.black,
            R.color.colorPrimary,R.color.colorAccent,R.color.black,
            R.color.colorAccent,R.color.blue,R.color.colorPrimary,
            R.color.blue,R.color.blue,R.color.blue};


    public Discover_Adapter_two.onClick itemclick;

    public interface onClick{
        void clickAction(int pos);
    }


    public Discover_Adapter_two(Discover_Adapter_two.onClick itemclick) {
        this.itemclick = itemclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discover_item_rl2,null);
        return new ViewHolder(v);
    }


    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imageView.setClipToOutline(true);
            viewHolder.RL.setClipToOutline(true);
        }

        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(text[i]);
        viewHolder.textView1.setText(text1[i]);
        viewHolder.textView1.setTextColor(text2[i]);

        viewHolder.onbind(i,itemclick);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView,textView1;
        RelativeLayout RL;
        android.support.v4.app.FragmentManager FM;
        android.support.v4.app.FragmentTransaction FT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.discover_rl_2_item_IV_id);
            textView = (TextView) itemView.findViewById(R.id.discover_rl_2_item_TV1_id);
            textView1 = (TextView) itemView.findViewById(R.id.discover_rl_2_item_TV2_id);
            RL = (RelativeLayout) itemView.findViewById(R.id.discover_rl_2_item_parent_id);

        }

        public void onbind(final int pos, final Discover_Adapter_two.onClick listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickAction(pos);
                }

            });

        }
    }
}
