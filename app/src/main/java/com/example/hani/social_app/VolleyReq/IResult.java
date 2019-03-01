package com.example.hani.social_app.VolleyReq;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IResult {
     void notifySuccess(String requestType, JSONObject response);
     void notifyError(String requestType, VolleyError error);
}
