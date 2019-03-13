package com.example.hani.social_app.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hani.social_app.Category.CategoryModel.CategoryModel;
import com.example.hani.social_app.Category.Category_Wise_News.More_details;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.TopNews.DataModels.NewsDataMode;
import com.example.hani.social_app.TopNews.Discover_Adapter_two;
import com.example.hani.social_app.TopNews.NewsDetail_f.NewsDetail_F;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder>  {



        Integer[] img = {R.drawable.aaa,R.color.blue,R.drawable.aaa,R.drawable.aaa
        ,R.drawable.aaa,R.color.black,R.drawable.aaa,R.drawable.aaa
        ,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa,R.drawable.aaa};

        String[] text = {"NEWS",
        "TECHNOLOGY",
        "FLIPBOARD PICKS",
        "CONNECT YOUR",
        "News",
        "BUSINESS",
        "News",
        "SPORTS",
        "ARTS",
        "News",
        "FASHION",
        "WALLPAPERS"};

    String[] text1 = {"By Flipboard News Desk",
            "By Flipboard News Desk",
            "By Flipboard",
            "By Jhon",
            "News",
            "By Flipboard News Desk",
            "By Flipboard",
            "By Smith",
            "By Flipboard News Desk",
            "News",
            "By Flipboard",
            "By Flipboard"};


    @NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_rl,null);
        return new ViewHolder(v);
        }


    public Category_Adapter.onClick itemclick;
    private List<CategoryModel> Category_list;
    private List<CategoryModel> Category_list_Filtered;
    Context context;
    public interface onClick{
        void clickAction(int pos);
    }
    public Category_Adapter(Category_Adapter.onClick itemclick, List<CategoryModel> Category_list, Context context) {
        this.itemclick = itemclick;
        this.Category_list=Category_list;
        this.Category_list_Filtered=Category_list;
        this.context = context;

    }
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    Category_list = Category_list_Filtered;
//                } else {
//                    List<CategoryModel> filteredList = new ArrayList<>();
//                    for (CategoryModel row : Category_list) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
//                            Log.d("cat",""+row.getName());
//                            filteredList.add(row);
//                        }
//                    }
//
//                    Category_list_Filtered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = Category_list_Filtered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                Category_list_Filtered = (ArrayList<CategoryModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public void updateList(ArrayList<CategoryModel> list){
        Category_list = list;
        notifyDataSetChanged();
    }
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        viewHolder.imageView.setClipToOutline(true);
        }

        final CategoryModel cate = Category_list.get(i);
        Picasso.get()
                .load(Variables.BASEURL+cate.getImage_url())
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);


        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Recycle Click " + i+" Like "+cate.getName()+" ", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(view.getContext(), More_details.class);
                myIntent.putExtra("cat_id",  cate.getCate_id());
                myIntent.putExtra("cate_name",  cate.getName());
                myIntent.putExtra("is_section",  "0");
                //Optional parameters
                view.getContext().startActivity(myIntent);

            }
        });
//

//        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(cate.getName());
//        viewHolder.textView1.setText(cate.getCate_id());

        }

    @Override
public int getItemCount() {
        return Category_list.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView,textView1;
    CardView main;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.category_item_imageview_id);
        textView = (TextView) itemView.findViewById(R.id.category_item_rl_tv_id);
        textView1 = (TextView) itemView.findViewById(R.id.category_item_rl_tv2_id);
        main = itemView.findViewById(R.id.category_item_cardview_id);
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
