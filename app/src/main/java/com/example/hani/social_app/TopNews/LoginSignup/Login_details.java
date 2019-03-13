package com.example.hani.social_app.TopNews.LoginSignup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hani.social_app.CodeClasses.Variables;
import com.example.hani.social_app.MainActivity;
import com.example.hani.social_app.R;
import com.example.hani.social_app.SharedPref.SharedPrefrence;
import com.example.hani.social_app.VolleyReq.IResult;
import com.example.hani.social_app.VolleyReq.VolleyService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class Login_details extends BottomSheetDialogFragment implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener  {

    private ProgressDialog pDialog;
    boolean is_wifi_availeable;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    String user_id, email, password, name;

    View view, Login_layout;
    View v;
    ImageView IV,instagram,cross;
    HorizontalScrollView HSV;
    TextView TV;
    RelativeLayout without_login_layout, already_login_layout;
    String User_info_json;
    Button butt_sign_up;
    private static final String EMAIL = "email";

    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    LoginButton loginButton;

    Api.Client mGoogleSignInClient;
    //public static final int RC_SIGN_IN = 0;
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    JSONObject jsonObj_gmail, jsonObj_fb;

    public Login_details() {
    }


    public void setDialogBorder(Dialog dialog) {
        FrameLayout bottomSheet = (FrameLayout) dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));
        setMargins(bottomSheet, 10, 0, 10, 20);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setContentView(R.layout.login_details);

        try {
            Field mBehaviorField = bottomSheetDialog.getClass().getDeclaredField("mBehavior");
            mBehaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) mBehaviorField.get(bottomSheetDialog);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return bottomSheetDialog;
    }



//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Dialog d = super.onCreateDialog(savedInstanceState);
//       d.setCanceledOnTouchOutside(false);
//
//
//
////        // view hierarchy is inflated after dialog is shown
////        d.setOnShowListener(new DialogInterface.OnShowListener() {
////            @Override
////            public void onShow(DialogInterface dialogInterface) {
////                //this disables outside touch
////                d.getWindow().findViewById(R.id.touch_outside).setOnClickListener(null);
////                //this prevents dragging behavior
////                View content = d.getWindow().findViewById(R.id.design_bottom_sheet);
////                ((CoordinatorLayout.LayoutParams) content.getLayoutParams()).setBehavior(null);
////            }
////        });
//        return d;
//    }



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        view = inflater.inflate(R.layout.login_details, container, false);


        v = view.findViewById(R.id.login_details_view_id);
        without_login_layout = view.findViewById(R.id.login_details_RL1_id);
      //  already_login_layout = view.findViewById(R.id.already_login);
        loginButton =  view.findViewById(R.id.login_details_fb_iV_id);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setFragment(this);
        // Bottom sheet height
        // this.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_round);

//        final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
//        behavior.setHideable(true);



        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getResources().getString(R.string.loading_text));
        pDialog.setCancelable(false);

        //// Check Network Availability
        is_wifi_availeable=Variables.is_internet_avail(getContext());


        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
        // End Key hash code

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);

            }
            @Override
            public void onCancel() {
                Variables.toast_msg(getContext(),"You cancel this.");
            }
            @Override
            public void onError(FacebookException error) {
                Variables.toast_msg(getContext(),""+error.toString());
                Log.d("error",""+error.toString());
            }
        });

//        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) v.getParent());
//        mBehavior.setPeekHeight(50);


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
               // getActivity().getSupportFragmentManager().popBackStack();
                dismiss();
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

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // Google Signin
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

        google_sign_in();


        ImageView gmail_sign_in = view.findViewById(R.id.google);

        gmail_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
            

        ImageView fb = view.findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
                //loginButton.setOnClickListener(this);
                loginButton.performClick();
            }
        });




        return view;

    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String fb_user_id = object.getString("id");
                            String imgurl = "https://graph.facebook.com/"+object.getString("id")+"/picture";
                            String fb_name = object.getString("name");
                            String fb_email = object.getString("email");

                            Variables.toast_msg(getContext(),""+fb_name);

                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("fb_name", fb_name); // Set the first name/pair
                            jsonObj.put("fb_email", fb_email);
                            jsonObj.put("fb_user_id" , fb_user_id);
                            jsonObj.put("fb_user_img", imgurl);


                            sign_in_fb(fb_email,fb_name);

                           // Variables.toast_msg(getContext(),"FB json object "+jsonObj.toString());
                                // ==> Bottom Sheet disappers
                           // dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Variables.toast_msg(getContext(),""+e.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode,  data);

        /// Gmail ignin
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
       // Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            try{
                name = acct.getDisplayName();
                user_id = acct.getId();
                String img_url = "https://plus.google.com/s2/photos/profile/" +user_id + "?sz=100";
//                Uri personPhotoUrl = acct.getPhotoUrl();
//                String img_yrl = personPhotoUrl.toString();
                email = acct.getEmail();

//                jsonObj_gmail = new JSONObject();
//                jsonObj_gmail.put("g_name", personName); // Set the first name/pair
//                jsonObj_gmail.put("g_email", email);
//                jsonObj_gmail.put("g_user_id" , user_id);


//                Variables.toast_msg(getContext(),"Gmail json object "+jsonObj.toString());
                //Variables.Log_d_msg(getContext(),"Gmail json object "+jsonObj.toString());

                // => Check If user Already member.

                sign_in_fb(email,name);


            }catch (Exception v){
                Variables.toast_msg(getContext(),""+v.toString());

            }



//            Log.e(TAG, "Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);

//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);
//
//            updateUI(true);


        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void google_sign_in()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .enableAutoManage(getActivity(), this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        Context context = getContext();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //mGoogleSignInTextView.setText(connectionResult.getErrorMessage());
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    }
    @Override
    public void onStart() {
        super.onStart();

//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//           // Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//           // showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                   // hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
    }

    public void sign_in_fb(String email,String password){

        Variables.showpDialog(pDialog);
//        email = EditEmail.getText().toString();
//        password = EditPassword.getText().toString();
        initVolleyCallback(email,password);


//        email":"a@a.com
        mVolleyService = new VolleyService(mResultCallback,getContext());
        JSONObject sendObj = null;
        //Variables.toast_msg(getContext(),"Email "+email+" Pass "+password);
        try {
            sendObj = new JSONObject("{'email': '"+email+"' , 'password': '"+password+"' }");
            //Variables.toast_msg(getContext(),"Email "+email+" Pass "+password+" "+sendObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.LoginApi, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback(final String ca_email, final String cal_password){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                // Add User info Json into Shared Prefrence

//                Variables.Log_d_msg(getContext(),"In SIgn In "+response.toString()+"" +
//                        "Cre Email "+ca_email+" Pass "+cal_password);

                    if(response.toString().contains("201") && response.toString().contains("already exist")){
                    // That means user is not a member  ==> Trigger Signup method;
                        Toast.makeText(getContext(), ""+response.toString(), Toast.LENGTH_SHORT).show();
                              sign_up(cal_password,ca_email,cal_password);

                             Variables.Log_d_msg(getContext(),"Name "+name+" Ema "+email+" Pass "+name);
                    }else if(response.toString().contains("201") && response.toString().contains("Incorrect login")){

                        Toast.makeText(getContext(), "InValid "+response.toString(), Toast.LENGTH_SHORT).show();
                        sign_up(cal_password,ca_email,cal_password);

                        Variables.Log_d_msg(getContext(),"Name "+name+" Ema "+email+" Pass "+name);


                    }else{
                        // => User already a memver
                        // Get All data and add into Shared Prefrence
                       // Toast.makeText(getContext(), "In Login "+response.toString(), Toast.LENGTH_SHORT).show();
                        SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_user_login_detail_key);
                            dismiss();
                    }



            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                //Log.d(TAG, "Volley requester " + requestType);
                Toast.makeText(getContext(), "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }



    public void sign_up(String name, String email, String password)
    {
        Variables.showpDialog(pDialog);
        String last_name = null;
//        String last_name = edit_last_name.getText().toString();
//        email = edit_email.getText().toString();
//        password = edit_password.getText().toString();
        int role = 1;

        Variables.toast_msg(getContext(),"Email "+email+" Pa "+password);

        initVolleyCallback_sign_up(email,password);

//        email":"a@a.com
        mVolleyService = new VolleyService(mResultCallback,getContext());
        //  mVolleyService.getDataVolley("GETCALL","http://192.168.1.150/datatest/get/data");
        JSONObject sendObj = null;

        try {
            sendObj = new JSONObject("{'email': '"+email+"' , 'password': '"+password+"' ," +
                    " 'first_name': '"+name+"' , 'last_name': '"+name+"' , " +
                    "'role': ' "+role+"' }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mVolleyService.postDataVolley("POSTCALL", Variables.SignUpApi, sendObj);

    }
    // Initialize Interface Call Backs
    void initVolleyCallback_sign_up(final String cal_email, final String cal_password){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.hidepDialog(pDialog);
                // Add User info Json into Shared Prefrence
                Variables.Log_d_msg(getContext(),"In SIgn Up "+response.toString()+"" +
                        "EMa "+cal_email+" pass "+cal_password);
                 //SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_user_login_detail_key);

                if(response.toString().contains("201") && response.toString().contains("already exist")){
                    // Already Existw
                    sign_in_fb(cal_email,cal_password);

//                    Variables.Log_d_msg(getContext(),"Volley requester " + requestType);
//                    Variables.toast_msg(getContext(),""+response.toString());
//                    Variables.Log_d_msg(getContext(),""+response);

//                    dismiss();

                }else{


                    Variables.toast_msg(getContext(),"Ok "+response.toString());

                    SharedPrefrence.save_response_share(getContext(),response.toString(),SharedPrefrence.shared_user_login_detail_key);

                    dismiss();

                }

                Variables.Log_d_msg(getContext(),"Volley requester " + requestType);
                Variables.toast_msg(getContext(),""+response.toString());
                Variables.Log_d_msg(getContext(),""+response);


            }



            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.hidepDialog(pDialog);
                Variables.toast_msg(getContext(),"Error "+error.toString());
                Variables.Log_d_msg(getContext(),""+error.toString());



            }
        };
    }



}



