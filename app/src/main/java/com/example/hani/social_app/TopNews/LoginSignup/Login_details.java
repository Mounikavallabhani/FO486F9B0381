package com.example.hani.social_app.TopNews.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;

public class Login_details extends Fragment {

    View view, Login_layout;
    View v;
    ImageView IV,instagram,cross;
    HorizontalScrollView HSV;
    TextView TV;
    RelativeLayout without_login_layout, already_login_layout;
    String User_info_json;
    Button butt_sign_up;
    public Login_details() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.login_details, container, false);
        v = view.findViewById(R.id.login_details_view_id);
        without_login_layout = view.findViewById(R.id.login_details_RL1_id);
      //  already_login_layout = view.findViewById(R.id.already_login);

        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.height = (int) (Variables.height * 0.35);
        v.setLayoutParams(lp);
        butt_sign_up = view.findViewById(R.id.button_sign_up);
        HSV = (HorizontalScrollView) view.findViewById(R.id.login_details_scrollview_id);
        cross = (ImageView) view.findViewById(R.id.login_details_cross_IV_id);
        TV = (TextView) view.findViewById(R.id.login_details_RL3_TV2_id);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        butt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), SignUp.class));

            }
        });
        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        /// Check If user login or Logout




        return view;

    }

}
