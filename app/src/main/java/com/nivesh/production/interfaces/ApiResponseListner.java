package com.nivesh.production.interfaces;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

public interface ApiResponseListner {

    public void onSuccessResponse(String strRequestTag, JSONObject jsonObject);

    public void onErrorResponse(String strRequestTag, ANError anError);

}
