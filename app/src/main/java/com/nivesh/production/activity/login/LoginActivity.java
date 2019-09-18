package com.nivesh.production.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivesh.production.R;
import com.nivesh.production.activity.baseActivity.BaseDrawerActivity;
import com.nivesh.production.activity.registeration.RegisterActivity;
import com.nivesh.production.utils.SharedPreferenceUtils;
import com.nivesh.production.utils.Utils_Functions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nivesh.production.utils.Connectivity.isConnectedFast;
import static com.nivesh.production.utils.Utils_Functions.fullScreen;
import static com.nivesh.production.utils.Utils_Functions.getNetworkClass;
import static com.nivesh.production.utils.Utils_Functions.showLog;
import static com.nivesh.production.utils.Utils_Variables.isInternet;
import static com.nivesh.production.utils.Utils_Variables.isInternetFast;
import static com.nivesh.production.utils.Utils_Variables.isShowPassword;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    SharedPreferenceUtils mSharedPreferenceUtils;
    Spannable mSpannable;

    @BindView(R.id.edt_login_password)
    EditText edt_login_password;

    @BindView(R.id.iv_password_toggle)
    ImageView iv_password_toggle;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.tv_no_account_title)
    TextView tv_no_account_title;

    @BindView(R.id.tv_login_agree_title)
    TextView tv_login_agree_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(LoginActivity.this);
        setContentView(R.layout.activity_logins);
        ButterKnife.bind(this);
        mContext = LoginActivity.this;

        initLogin();
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private void initLogin() {
        mSharedPreferenceUtils = SharedPreferenceUtils.getInstance(mContext);
        dontHaveAccountText();
        termsConditionText();

        new Utils_Functions.InternetCheck(internet -> { /* do something with boolean response */
            isInternet = internet;
            isInternetFast = isConnectedFast(mContext);

            if (isInternet && isInternetFast) {
                showLog("isInternet  ", String.valueOf(internet));
                showLog("isConnectedFast  ", String.valueOf(isConnectedFast(mContext)));
                showLog("getNetworkClass  ", String.valueOf(getNetworkClass(mContext)));
            } else if (!isInternetFast && isInternet) {
                showLog("isInternet  ", String.valueOf(internet));
                showLog("isConnectedFast  ", String.valueOf(isConnectedFast(mContext)));
                showLog("getNetworkClass  ", String.valueOf(getNetworkClass(mContext)));
            } else {
                showLog("isInternet  ", String.valueOf(internet));
            }


//            if (isInternetFast) {
//                showLog("isConnectedFast  ", String.valueOf(isConnectedFast(mContext)), "", "");
//            } else {
//                showLog("isConnectedFast  ", String.valueOf(isConnectedFast(mContext)), "", "");
//            }


        });


        initLoginClick();

    }

    private void initLoginClick() {

        iv_password_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPassword) {
                    isShowPassword = false;
                    // show password
                    edt_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    // set cursor to end to the text's
                    edt_login_password.setSelection(edt_login_password.getText().length());

                    // password toggle image icon to hide
                    iv_password_toggle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_eye_blue_dark_close));

                } else {                        // default condition
                    isShowPassword = true;
                    // hide password
                    edt_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    // set cursor to end to the text's
                    edt_login_password.setSelection(edt_login_password.getText().length());

                    // password toggle image icon to hide
                    iv_password_toggle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_eye_blue_dark_open));
                }
            }
        });


        btn_login.setOnClickListener((View v) -> {
            Intent intentChat = new Intent(mContext, BaseDrawerActivity.class);
            startActivity(intentChat);
        });

    }

    private void dontHaveAccountText() {
        mSpannable = new SpannableString(getResources().getString(R.string.txt_no_account));

        mSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), 27, 34, 0);

        // set underline text
        mSpannable.setSpan(new UnderlineSpan(), 27, 34, 0);


        // clickable text
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intentChat = new Intent(mContext, RegisterActivity.class);
                startActivity(intentChat);
            }
        };
        mSpannable.setSpan(clickableSpan, 27, 34, 0);
        tv_no_account_title.setText(mSpannable);
        tv_no_account_title.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void termsConditionText() {

        mSpannable = new SpannableString(getResources().getString(R.string.txt_agreee));

        // change text color
        mSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorRed_nivesh)), 27, 45, 0);

        // url for terms and conditions
        mSpannable.setSpan(new URLSpan("https://www.google.co.in"), 27, 45, 0);  // if it enable then we use t&c policy
        tv_login_agree_title.setText(mSpannable);
        tv_login_agree_title.setMovementMethod(LinkMovementMethod.getInstance());  // mendate for click to open t&c

    }

}
