package com.example.hani.social_app.TopNews.LoginSignup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class Already_Login_f extends BottomSheetDialogFragment {

    View view, Login_layout;
    View v;
    ImageView IV,instagram,cross;
    HorizontalScrollView HSV;
    TextView TV, text_name, text_email;
    RelativeLayout without_login_layout, already_login_layout;
    String User_info_json;
    Button butt_sign_up;
   CallbackManager callbackManager;
    public Already_Login_f() {
    }


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.loading_layout, container, false);
        User_info_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);
        text_name = view.findViewById(R.id.login_details_RL2_TV2_id);
        text_email = view.findViewById(R.id.email_text);

//        setDialogBorder(view);

        // Bottom sheet height
//
//        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) v.getParent());
//        mBehavior.setPeekHeight(100);
        // Get user Data From Json
        try{
            Variables.Log_d_msg(getContext(),""+User_info_json);
            //Variables.toast_msg(getContext(),""+User_info_json);
            JSONObject response = new JSONObject(User_info_json);
            JSONObject Arr = response.getJSONObject("msg");
            JSONObject user = Arr.getJSONObject("User");
//            Arr.getString("first_name");
//            Arr.getString("last_name");
            text_name.setText(user.getString("first_name")+" "+user.getString("last_name"));
            text_email.setText(user.getString("email"));
        }catch (Exception y){
            Variables.toast_msg(getContext(),""+y.toString());
        }


        v = view.findViewById(R.id.login_details_view_id);
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = (int) (Variables.height * 0.48);
        v.setLayoutParams(lp);
        butt_sign_up = view.findViewById(R.id.button_sign_up);
        HSV = (HorizontalScrollView) view.findViewById(R.id.login_details_scrollview_id);
        cross = (ImageView) view.findViewById(R.id.login_details_cross_IV_id);
//        TV = (TextView) view.findViewById(R.id.login_details_RL3_TV2_id);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().popBackStack();
                dismiss();

            }
        });
        butt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(getActivity(), SignUp.class));
                SharedPrefrence.logout_user(getContext());
                //Variables.toast_msg(getContext(),"logout");
                //getActivity().getSupportFragmentManager().popBackStack();
                LoginManager.getInstance().logOut();
                dismiss();

            }
        });



        return view;
    }

    }
