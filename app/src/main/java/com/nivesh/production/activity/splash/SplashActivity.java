package com.nivesh.production.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nivesh.production.R;
import com.nivesh.production.activity.login.LoginActivity;

import butterknife.ButterKnife;

import static com.nivesh.production.utils.Utils_Functions.fullScreen;
import static com.nivesh.production.utils.Utils_Functions.showLog;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(SplashActivity.this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        mContext = SplashActivity.this;
        finishSplash();

    }


    private void finishSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLog("SendData ", "ok");
//                startActivity(new Intent(mContext, LoginActivity.class));

                Intent intentChat = new Intent(mContext, LoginActivity.class);
                startActivity(intentChat);
                finish();
            }
        }, 2000);

    }

}
