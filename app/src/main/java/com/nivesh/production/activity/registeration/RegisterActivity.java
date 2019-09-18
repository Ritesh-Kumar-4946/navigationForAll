package com.nivesh.production.activity.registeration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.nivesh.production.R;
import com.nivesh.production.activity.baseActivity.BaseDrawerActivity;
import com.nivesh.production.activity.splash.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nivesh.production.utils.Utils_Functions.fullScreen;
import static com.nivesh.production.utils.Utils_Functions.showLog;
import static com.nivesh.production.utils.Utils_Variables.isShowPassword;

public class RegisterActivity extends AppCompatActivity {

    Context mContext;

    @BindView(R.id.tv_agree_title)
    TextView tv_agree_title;

    @BindView(R.id.tv_account_title)
    TextView tv_account_title;

    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.iv_reg_password_toggle)
    ImageView iv_reg_password_toggle;

    @BindView(R.id.edt_reg_password)
    EditText edt_reg_password;


    Spannable mSpannable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(RegisterActivity.this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        mContext = RegisterActivity.this;

        initRegistration();
    }
    static {
//        https://stackoverflow.com/questions/39419596/resourcesnotfoundexception-file-res-drawable-abc-ic-ab-back-material-xml
//        BY ADDING THIS WE CAN PROTECT CRASHING IN OLD MOBILE FOR SVG ICONS USAGE
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private void initRegistration() {
        alreadyAccountText();
        termsConditionText();
        initRegistrationClick();
    }

    private void alreadyAccountText() {
        mSpannable = new SpannableString(getResources().getString(R.string.txt_already_account));

        // change text color
//        mSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRed_nivesh)), 25, 32, 0);
        mSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), 26, 32, 0);

        // set underline text
        mSpannable.setSpan(new UnderlineSpan(), 25, 32, 0);


        // clickable text
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // We display a Toast. You could do anything you want here.
//                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        };
        mSpannable.setSpan(clickableSpan, 25, 32, 0);
        tv_account_title.setText(mSpannable);
        tv_account_title.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void termsConditionText() {

        mSpannable = new SpannableString(getResources().getString(R.string.txt_agreee));

        // change text color
        mSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorRed_nivesh)), 27, 45, 0);

        // url for terms and conditions
        mSpannable.setSpan(new URLSpan("https://www.google.co.in"), 27, 45, 0);  // if it enable then we use t&c policy
        tv_agree_title.setText(mSpannable);
        tv_agree_title.setMovementMethod(LinkMovementMethod.getInstance());  // mendate for click to open t&c

    }

    private void initRegistrationClick() {

        iv_reg_password_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPassword) {
                    isShowPassword = false;
                    // show password
                    edt_reg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    // set cursor to end to the text's
                    edt_reg_password.setSelection(edt_reg_password.getText().length());

                    // password toggle image icon to hide
                    iv_reg_password_toggle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_eye_blue_dark_close));

                } else {                        // default condition
                    isShowPassword = true;
                    // hide password
                    edt_reg_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    // set cursor to end to the text's
                    edt_reg_password.setSelection(edt_reg_password.getText().length());

                    // password toggle image icon to hide
                    iv_reg_password_toggle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_eye_blue_dark_open));
                }
            }
        });

        btn_register.setOnClickListener((View v)->{
            Intent intentChat = new Intent(mContext, BaseDrawerActivity.class);
            startActivity(intentChat);
        });

    }

}
