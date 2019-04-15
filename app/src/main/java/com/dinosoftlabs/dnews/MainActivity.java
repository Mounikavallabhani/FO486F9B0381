package com.dinosoftlabs.dnews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.dinosoftlabs.dnews.VolleyReq.IResult;
import com.dinosoftlabs.dnews.VolleyReq.VolleyService;
import com.dinosoftlabs.dnews.social_app.R;

public class MainActivity extends AppCompatActivity {
    RelativeLayout RL;
    private String TAG = "MainActivity";
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    boolean is_wifi_availeable;

//    Run time Permission

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    //  Toast.makeText(NameManually.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();

                    Variables.alert_dialogue(MainActivity.this,"" + getResources().getString(R.string.per_title),
                            "" + getResources().getString(R.string.per_info));

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is_wifi_availeable = Variables.is_internet_avail(this);


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,


                },
                1);




        RL = (RelativeLayout) findViewById(R.id.mainactivity_RL_id);

        MainFragment F = new MainFragment();
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.addToBackStack(null);
        FT.replace(R.id.mainactivity_RL_id,F).commit();




    }



}
