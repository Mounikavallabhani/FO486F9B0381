package com.dinosoftlabs.dnews.FireBase;

import com.dinosoftlabs.dnews.CodeClasses.Variables;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Variables.Log_d_msg(getApplication(),""+"From: " + remoteMessage.getFrom());
        Variables.Log_d_msg(getApplication(),""+"Notification Message Body: " + remoteMessage.getNotification().getBody());
       //Log.d(TAG, );

    }

}
