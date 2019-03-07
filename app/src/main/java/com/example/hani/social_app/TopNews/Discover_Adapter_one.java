package com.example.hani.social_app.TopNews;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Discover_Adapter_one extends RecyclerView.Adapter<Discover_Adapter_one.ViewHolder> {



    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};
    int width;
    public Discover_Adapter_one.onClick itemclick;
    private List<NewsDataMode> News_List_adapter;
    public Discover_Adapter_one(Discover_Adapter_one.onClick itemclick, List<NewsDataMode> news_List) {
        this.itemclick = itemclick;
        this.News_List_adapter=news_List;
    }
    public interface onClick{
        void clickAction(int pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discover_item_rl1,null);
//        return new ViewHolder(v);

//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View itemView = inflater.inflate(R.layout.discover_item_rl1, null);

        //ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) itemView.getLayoutParams();
        //layoutParams.width = Variables.width - 100;
//        width = (int) (viewGroup.getMeasuredWidth() * 0.8);
//        v.setMinimumWidth(width);

        return new ViewHolder(v);


    }

    @Override
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

      // viewHolder.imageView.setImageResource(img[i]);

    }

    @Override
    public int getItemCount() {
        return News_List_adapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_recyclerview_IV_id);



        }
        public void onbind(final int pos, final Discover_Adapter_one.onClick listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickAction(pos);
                }

            });

        }
    }
}
