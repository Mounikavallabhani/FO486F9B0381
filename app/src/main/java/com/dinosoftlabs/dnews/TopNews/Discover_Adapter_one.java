package com.dinosoftlabs.dnews.TopNews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.TopNews.DataModels.Sliders_data_model;
import com.dinosoftlabs.dnews.TopNews.NewsDetail_f.NewsDetail_F;
import com.dinosoftlabs.dnews.social_app.R;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.List;

public class Discover_Adapter_one extends RecyclerView.Adapter<Discover_Adapter_one.ViewHolder> {



    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};
    int width;
    String decodedString;
    public Discover_Adapter_one.onClick itemclick;
    private List<Sliders_data_model> News_List_adapter;
    Context context;
    String title;
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
       // Log.d("data",""+News.getImage_url());
//        viewHolder.imageView.setBackgroundResource(R.drawable.aaa);
        int targetWidth = viewHolder.imageView.getWidth();

        Picasso.get()
                .load(Variables.BASEURL + News.getImage_url()).
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

//        try{
// decodedString = URLDecoder.decode(News.getTitle() , "UTF-8");
//}catch (Exception v){
//
//}

        title = News.getTitle();

        title = Html.fromHtml(title).toString();

//        if (title.length() > 20)
//        {
//            decodedString = title.substring(0, 20);
//        }
//        else
//        {
            decodedString = title;
        //}


        viewHolder.title.setText("" + decodedString);
        viewHolder.Cate_name.setText(News.getCategory());


        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Recycle Click " + i +" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();

                boolean is_wifi_availeable = Variables.is_internet_avail(context);

                if(is_wifi_availeable == true) {
                    /// If wifi Available
                    Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                    myIntent.putExtra("news_id",  News.getNews_id());
                    myIntent.putExtra("like_or_dislike", News.getLike_dislike());
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
        TextView title, Cate_name;
            CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_recyclerview_IV_id);
            main = itemView.findViewById(R.id.main);
            card = itemView.findViewById(R.id.item_recyclerview_CV_id);
            title = itemView.findViewById(R.id.title);
            Cate_name = itemView.findViewById(R.id.cate_name);
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
