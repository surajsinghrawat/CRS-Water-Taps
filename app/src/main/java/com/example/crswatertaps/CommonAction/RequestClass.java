package com.example.crswatertaps.CommonAction;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestClass extends JsonObjectRequest {


    public static final String server="https://crsmailfunction.herokuapp.com/api/mail/";


    Context context;
    public RequestClass(int method, @Nullable JSONObject jsonRequest,
                        Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener,Context mContext) {
        super(method, server, jsonRequest, listener, errorListener);
        this.context=mContext;
    }


    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        headers.put("AppId", "xyz");

        return headers;
    }


}
