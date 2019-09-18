package com.nivesh.production.activity.baseActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nivesh.production.interfaces.ApiResponseListner;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

import static com.nivesh.production.utils.Utils_Functions.isOnline;
import static com.nivesh.production.utils.Utils_Functions.showLog;

public abstract class BaseActivity extends AppCompatActivity {

    Context mContext;

    ApiResponseListner apiResponseListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mContext = this;
        ButterKnife.bind(this);
        AndroidNetworking.initialize(this);

    }


    public void apiRequest_POST_JsonObj(boolean isProgressBar, final String strURL, JSONObject jObj_PostData, String strRequest_Tag) {

        showLog("BaseActivity", "apiRequest_POST_JsonObj-> URL: " + strURL + ",      strRequest_Tag: " + strRequest_Tag + ",     PostData: " + String.valueOf(jObj_PostData.toString()));

        if (isOnline(this)){
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

            AndroidNetworking.post(strURL)
                    .addJSONObjectBody(jObj_PostData)   // post jsonobject data here
                    .setPriority(Priority.MEDIUM)
                    .setOkHttpClient(okHttpClient)  // request customization
                    .setTag(strRequest_Tag)   // request tag used for cancellation of the request
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                            showLog("BaseActivity", "setAnalyticsListener_onReceived -> timeTakenInMillis : " + String.valueOf(timeTakenInMillis) + ",    bytesSent: " + String.valueOf(bytesSent) + ",    bytesReceived: " + String.valueOf(bytesReceived));

                        }
                    })
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {

                            showLog("BaseActivity", "setDownloadProgressListener_onProgress -> totalBytes : " + String.valueOf(totalBytes) + ",    bytesDownloaded: " + String.valueOf(bytesDownloaded));

                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
//                        pb_create_referrer_global.setVisibility(View.GONE);
//                        showLog("Raw_Response_All", String.valueOf(response.toString()), "", "");
//                        parseSubBokerDropDownData(response);
                            apiResponseListner.onSuccessResponse(strRequest_Tag, response);

                        }

                        @Override
                        public void onError(ANError anError) {
//                        pb_create_referrer_global.setVisibility(View.GONE);
//
//                        showLog("Raw_Response_Error ", anError.getMessage(), "", "");
                            apiResponseListner.onErrorResponse(strRequest_Tag, anError);
                        }

                    });
        } else {
            /// show dialog for offline
        }


    }

}
