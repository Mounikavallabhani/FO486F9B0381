package com.dinosoftlabs.dnews.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinosoftlabs.dnews.Category.Category_Wise_News.More_details;
import com.dinosoftlabs.dnews.Category.CategoryModel.CategoryModel;
import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.TopNews.Discover_Adapter_two;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder>  {



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
                .load(Variables.BASEURL + cate.getImage_url())
                .placeholder(R.mipmap.ic_dnews)
                .error(R.mipmap.ic_dnews)
                .into(viewHolder.imageView);


        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent myIntent = new Intent(view.getContext(), More_details.class);
                myIntent.putExtra("cat_id",  cate.getCate_id());
                myIntent.putExtra("cate_name",  cate.getName());
                myIntent.putExtra("is_section",  "0");
                //Optional parameters
                view.getContext().startActivity(myIntent);

            }
        });

        viewHolder.textView.setText(cate.getName());


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
