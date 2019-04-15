package com.dinosoftlabs.dnews.TopNews.LoginSignup;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.social_app.R;
import com.dinosoftlabs.dnews.SharedPref.SharedPrefrence;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

public class Already_Login_f extends BottomSheetDialogFragment {

    View view;
    View v;
    ImageView cross;
    HorizontalScrollView HSV;
    TextView text_name, text_email;
       String User_info_json;
    Button butt_sign_up;

    public Already_Login_f() {
    }


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.loading_layout, container, false);
        User_info_json = SharedPrefrence.get_offline(getContext(),SharedPrefrence.shared_user_login_detail_key);
        text_name = view.findViewById(R.id.login_details_RL2_TV2_id);
        text_email = view.findViewById(R.id.email_text);

        try{
            Variables.Log_d_msg(getContext(),""+User_info_json);

            JSONObject response = new JSONObject(User_info_json);
            JSONObject Arr = response.getJSONObject("msg");
            JSONObject user = Arr.getJSONObject("User");
            text_name.setText(user.getString("first_name")+" " + user.getString("last_name"));
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


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });
        butt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefrence.logout_user(getContext());

                LoginManager.getInstance().logOut();
                dismiss();

            }
        });



        return view;
    }

    }
