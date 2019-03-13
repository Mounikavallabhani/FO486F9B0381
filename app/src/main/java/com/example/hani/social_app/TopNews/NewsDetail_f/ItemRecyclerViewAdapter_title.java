package com.example.hani.social_app.TopNews.NewsDetail_f;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.DataModels.News_Section_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class ItemRecyclerViewAdapter_title extends RecyclerView.Adapter<ItemRecyclerViewAdapter_title.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemLabel;
        private ImageView image_view;
        private CardView card;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemLabel = (TextView) itemView.findViewById(R.id.item_label);
            image_view = itemView.findViewById(R.id.image_title);
            card = itemView.findViewById(R.id.cardView);
        }
    }

    private Context context;
    private List<News_Section_Model> News_section_List;

    public ItemRecyclerViewAdapter_title(Context context, List<News_Section_Model> News_section_List ) {
        this.context = context;
        this.News_section_List = News_section_List;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_row_layout, parent, false);
        Variables.Log_d_msg(parent.getContext(),"Size of Section N "+News_section_List.size());
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final News_Section_Model News = News_section_List.get(position);
        holder.itemLabel.setText(News.getTitle());
//recycler view for items

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Recycle Click " + position+" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                myIntent.putExtra("news_id",  News.getNews_id());
                myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
                //Optional parameters
                view.getContext().startActivity(myIntent);

            }
        });



        Picasso.get()
                .load(News.getImage_url()).fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(holder.image_view);



    }

    @Override
    public int getItemCount() {
        return News_section_List.size();
    }


}