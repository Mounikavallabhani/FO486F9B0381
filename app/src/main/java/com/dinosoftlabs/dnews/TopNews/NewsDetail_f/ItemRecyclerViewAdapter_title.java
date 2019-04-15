package com.dinosoftlabs.dnews.TopNews.NewsDetail_f;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinosoftlabs.dnews.TopNews.DataModels.News_Section_Model;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.List;

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
    String title;
    private List<News_Section_Model> News_section_List;
    String decodedString;
    public ItemRecyclerViewAdapter_title(Context context, List<News_Section_Model> News_section_List ) {
        this.context = context;
        this.News_section_List = News_section_List;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_row_layout, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final News_Section_Model News = News_section_List.get(position);

        title = News.getTitle();

//        if (title.length() > 60)
//        {
//            decodedString = title.substring(0, 60);
//        }
//        else
//        {
//            decodedString = title;
//        }

        holder.itemLabel.setText(""+ Html.fromHtml(title));



        //
//        try{
//            decodedString = URLDecoder.decode(, "UTF-8");
//        }catch (Exception v){
//
//        }
//
//
//        holder.itemLabel.setText(decodedString);
//recycler view for items

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Recycle Click " + position+" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();

                boolean is_wifi_availeable = Variables.is_internet_avail(context);

                if(is_wifi_availeable == true) {
                    /// If wifi Available

                    Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                    myIntent.putExtra("news_id",  News.getNews_id());
                    myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
                    myIntent.putExtra("title", News.getTitle());
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



        Picasso.get()
                .load(Variables.BASEURL + News.getImage_url()).fit()
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