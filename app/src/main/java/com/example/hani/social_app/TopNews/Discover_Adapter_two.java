package com.example.hani.social_app.TopNews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.DataModels.News_Section_Model;
import com.example.hani.social_app.TopNews.NewsDetail_f.ItemRecyclerViewAdapter_title;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Discover_Adapter_two extends RecyclerView.Adapter<Discover_Adapter_two.ViewHolder> {

//    Integer[] img = {R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
//            ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa
//            ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};
//
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

//
//
//    String[] text1 = {"FESTIVALS",
//            "ENVIORNMENT",
//            "ARTS",
//            "FASHION",
//            "BEAUTY",
//            "TRAVEL",
//            "ENVIORNMENT",
//            "ARTS",
//            "FASHION",
//            "BEAUTY",
//            "TRAVEL",
//            "FASHION"};
//
//    Integer[] text2 = {R.color.blue,R.color.colorAccent,R.color.black,
//            R.color.colorPrimary,R.color.colorAccent,R.color.black,
//            R.color.colorAccent,R.color.blue,R.color.colorPrimary,
//            R.color.blue,R.color.blue,R.color.blue};

   // private final View.OnClickListener mOnClickListener = new MyOnClickListener();
    public Discover_Adapter_two.onClick itemclick;
    private List<NewsDataMode> News_List_adapter;
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    ArrayList<String> list_News_title = new ArrayList<String>();
    ArrayList<String> list_News_img_urls = new ArrayList<String>();
    private List<News_Section_Model> News_section_List;

    Context context;
    View v;
    public interface onClick{
        void clickAction(int pos);
    }

    public Discover_Adapter_two(onClick itemclick, List<NewsDataMode> news_List,Context context) {
        this.itemclick = itemclick;
        this.News_List_adapter=news_List;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // New Code


        //        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        if (viewGroup == VIEW_TYPE_HEADER) {
//            v = inflater.inflate(R.layout.header, parent, false);
//            return new VHHeader(view);
//        } else if (viewType == VIEW_TYPE_ITEM) {
//            view = inflater.inflate(R.layout.item, parent, false);
//            return new VHItem(view);
//        }

        // End New Code
        News_section_List = new ArrayList<>();

        Variables.Log_d_msg(viewGroup.getContext(),"Size in 2nd Adapter "+News_List_adapter.size());
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discover_item_2,null);
        return new ViewHolder(v);

//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View cellView = inflater.inflate(R.layout.discover_item_2, viewGroup, false);
//        return new ViewHolder(cellView);


    }



    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            viewHolder.imageView.setClipToOutline(true);
   //         viewHolder.RL.setClipToOutline(true);
        }

//        final YourModel model = mModelList.get(position -1 ); // Subtract 1 for header
//
//        ModelHolder holder = (ModelHolder) h;



        final NewsDataMode News = News_List_adapter.get(i);
        viewHolder.sectionLabel.setText(News.getSection_title());
        JSONArray get_news= News.getNews_array();

        try{
            for(int a = 0; a < get_news.length(); a++){
                JSONObject news_obj = get_news.getJSONObject(a);
                JSONObject news = news_obj.getJSONObject("News");
                news.getString("title");
                news.getString("attachment");
                news.getInt("id");

              // list_News_title.add(""+news.getString("title"));
                //list_News_img_urls.add(""+news.getString("attachment"));

                News_Section_Model add = new News_Section_Model(news.getString("attachment")
                        ,news.getString("title"),
                        news.getInt("id"),
                        news.getInt("favourite")

                );
                News_section_List.add(add);


            }

        }catch (Exception v){
            Variables.Log_d_msg(context,""+list_News_title);
        }





        viewHolder.itemRecyclerView.setHasFixedSize(true);
        viewHolder.itemRecyclerView.setNestedScrollingEnabled(false);
        //recycler view for items
        viewHolder.itemRecyclerView.setHasFixedSize(true);
        viewHolder.itemRecyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        viewHolder.itemRecyclerView.setLayoutManager(gridLayoutManager);
        ItemRecyclerViewAdapter_title adapter = new ItemRecyclerViewAdapter_title(context,News_section_List);
        viewHolder.itemRecyclerView.setAdapter(adapter);
//        list_News_title.clear();






        // viewHolder.itemRecyclerView.setAdapter(adapter);

        //show toast on click of show all button

        viewHolder.itemRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked on : " + News.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });



//      //  viewHolder.imageView.setImageResource(img[i]);
//        Picasso.get()
//                .load(News.getImage_url()).fit()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_dnews)
//                .error(R.mipmap.ic_dnews)
//                .into(viewHolder.imageView);
//
////        News.getLike_dislile();
//        viewHolder.news_section.setText("Sec ");
//        viewHolder.textView.setText(News.getTitle());
//        viewHolder.textView1.setText(News.getCategory()+" Is Sec? "+News.getIs_section());
////        viewHolder.textView1.setTextColor(text2[i]);
//        viewHolder.onbind(i,itemclick);
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel, showAllButton;
        private RecyclerView itemRecyclerView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionLabel = (TextView) itemView.findViewById(R.id.section_label);
            showAllButton = (TextView) itemView.findViewById(R.id.section_show_all_button);
            itemRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_recycler_view);
        }
    }


    @Override
    public int getItemCount() {
        return News_List_adapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sectionLabel, showAllButton;
        private RecyclerView itemRecyclerView;
       // android.support.v4.app.FragmentManager FM;
       // android.support.v4.app.FragmentTransaction FT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionLabel =  itemView.findViewById(R.id.section_label);
            showAllButton =  itemView.findViewById(R.id.section_show_all_button);
            itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);

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
