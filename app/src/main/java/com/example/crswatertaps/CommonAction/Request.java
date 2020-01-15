package com.example.crswatertaps.CommonAction;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request extends JsonObjectRequest {



    public Request(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                   @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }
    @Override
    public Map<String,String> getHeaders() throws AuthFailureError {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization","iaY0wmuRlrQNQ0OfKOMcYNmkmsgXS1Tz");
        headers.put("Content-Type", "application/json");

        return headers;
    }
}
