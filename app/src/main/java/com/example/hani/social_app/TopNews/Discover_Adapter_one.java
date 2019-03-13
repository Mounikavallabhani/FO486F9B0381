package com.example.hani.social_app.TopNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.DataModels.Sliders_data_model;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class Discover_Adapter_one extends RecyclerView.Adapter<Discover_Adapter_one.ViewHolder> {



    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};
    int width;
    public Discover_Adapter_one.onClick itemclick;
    private List<Sliders_data_model> News_List_adapter;
    Context context;
    public Discover_Adapter_one(Discover_Adapter_one.onClick itemclick, List<Sliders_data_model> news_List,Context context) {
        this.itemclick = itemclick;
        this.News_List_adapter=news_List;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.imageView.setClipToOutline(true);
        }

        final Sliders_data_model News = News_List_adapter.get(i);
        Log.d("data",""+News.getImage_url());
//        viewHolder.imageView.setBackgroundResource(R.drawable.aaa);
        int targetWidth = viewHolder.imageView.getWidth();

        Picasso.get()
                .load(News.getImage_url()).
        resize(200, 250)
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews).
                into(viewHolder.imageView);



//        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.aaa);
//        notBuilder.setLargeIcon(largeIcon);

//        viewHolder.imageView.setImageResource(R.drawable.aaa);

//        Picasso.get().load(News.getImage_url()).error(R.mipmap.ic_dnews)
//                               .into(new Target(){
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                viewHolder.imageView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//
//                        @Override
//            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                Log.d("TAG", "Prepare Load");
//            }
//        });

       // viewHolder.card.setBackgroundResource(R.drawable.aaa);

        viewHolder.title.setText(""+News.getTitle());

        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Recycle Click " + i +" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                myIntent.putExtra("news_id",  News.getNews_id());
                myIntent.putExtra("like_or_dislike", News.getLike_dislike());
                //Optional parameters
                view.getContext().startActivity(myIntent);

            }
        });

      // viewHolder.imageView.setImageResource(img[i]);

//        Picasso.get()
//                .load(News.getImage_url()).fit()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_dnews)
//                .error(R.mipmap.ic_dnews)
//                .into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return News_List_adapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout main;
        TextView title;
            CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_recyclerview_IV_id);
            main = itemView.findViewById(R.id.main);
            card = itemView.findViewById(R.id.item_recyclerview_CV_id);
            title = itemView.findViewById(R.id.title);
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
