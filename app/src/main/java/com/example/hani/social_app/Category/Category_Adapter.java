package com.example.hani.social_app.Category;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hani.social_app.R;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {



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


public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        viewHolder.imageView.setClipToOutline(true);
        }
        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(text[i]);
        viewHolder.textView1.setText(text1[i]);

        }

@Override
public int getItemCount() {
        return img.length;
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView,textView1;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.category_item_imageview_id);
        textView = (TextView) itemView.findViewById(R.id.category_item_rl_tv_id);
        textView1 = (TextView) itemView.findViewById(R.id.category_item_rl_tv2_id);

    }
}
}
