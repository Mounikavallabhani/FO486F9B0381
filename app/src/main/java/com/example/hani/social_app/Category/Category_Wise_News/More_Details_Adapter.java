package com.example.hani.social_app.Category.Category_Wise_News;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.Category.Category_Adapter;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.DataModels.News_Section_Model;
import com.example.hani.social_app.TopNews.Discover_Adapter_two;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.squareup.picasso.Picasso;

import java.util.List;

public class More_Details_Adapter extends RecyclerView.Adapter<More_Details_Adapter.ViewHolder> {

    private ProgressDialog pDialog;

    Integer[] img = {R.drawable.aaa,R.color.blue,R.drawable.aaa,R.drawable.aaa
            ,R.drawable.aaa,R.color.black,R.drawable.aaa,R.drawable.aaa
            ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};

    String[] text = {"Top startups that are changing the way we travel changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel changing the way we travel",
            "Top startups that are changing the way we travel changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel",
            "Top startups that are changing the way we travel"};

    public More_Details_Adapter.onClick itemclick;
    private List<News_Section_Model> News_list;
//    private List<CategoryModel> Category_list_Filtered;
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
                .load(News.getImage_url()).fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);


       // viewHolder.imageView.setImageResource(img[i]);

        viewHolder.Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Recycle Click " + i +" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
                myIntent.putExtra("news_id",  News.getNews_id());
                myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
                //Optional parameters
                view.getContext().startActivity(myIntent);

            }
        });


        if(News.getIs_like_dis_like()==0){
            // Dislike Show
            viewHolder.Dis_like.setVisibility(View.VISIBLE);
        }else if(News.getIs_like_dis_like()==1){
            // Show Filled heart
            viewHolder.like.setVisibility(View.VISIBLE);
        }


        viewHolder.Dis_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Dislike " + News.getIs_like_dis_like() +" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();
// ========> Code for Dislike

   Variables.Like_News(0, News.getNews_id(),viewHolder.like,viewHolder.Dis_like,pDialog,context);

//                Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
//                myIntent.putExtra("news_id",  News.getNews_id());
//                myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
//                //Optional parameters
//                view.getContext().startActivity(myIntent);



            }
        });


        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Like " + News.getIs_like_dis_like() +" Like "+News.getIs_like_dis_like()+" ", Toast.LENGTH_SHORT).show();
// ========> Code for like

                Variables.Like_News(1, News.getNews_id(),viewHolder.like,viewHolder.Dis_like,pDialog,context);


//                Intent myIntent = new Intent(view.getContext(), NewsDetail_F.class);
//                myIntent.putExtra("news_id",  News.getNews_id());
//                myIntent.putExtra("like_or_dislike", News.getIs_like_dis_like());
//                //Optional parameters
//                view.getContext().startActivity(myIntent);

            }
        });




        viewHolder.textView.setText(News.getTitle());

    }

    @Override
    public int getItemCount() {
        return News_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        RelativeLayout Main;
        ImageView like, Dis_like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.more_details_item_IV_id);
            textView = (TextView) itemView.findViewById(R.id.more_details_item_TV_id);
            Main = itemView.findViewById(R.id.main);
            like = itemView.findViewById(R.id.like);
            Dis_like = itemView.findViewById(R.id.dis_like);

        }
    }
}
