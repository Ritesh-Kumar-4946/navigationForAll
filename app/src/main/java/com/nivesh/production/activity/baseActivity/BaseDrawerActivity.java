package com.nivesh.production.activity.baseActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nivesh.production.R;
import com.nivesh.production.fragment.Alerts_DLFragment;
import com.nivesh.production.fragment.ChangePasword_DLFragment;
import com.nivesh.production.fragment.Clients_DLFragment;
import com.nivesh.production.fragment.Contact_DLFragment;
import com.nivesh.production.fragment.Contents_DLFragment;
import com.nivesh.production.fragment.DailyTransections_DLFragment;
import com.nivesh.production.fragment.Exit_DLFragment;
import com.nivesh.production.fragment.LanguageSwitch_DLFragment;
import com.nivesh.production.fragment.Marketing_DLFragment;
import com.nivesh.production.fragment.SipDecline_DLFragment;
import com.nivesh.production.fragment.SipDetail_DLFragment;
import com.nivesh.production.fragment.ViewOrders_DLFragment;
import com.nivesh.production.fragment.dashboard.DashBoard_DLFragment;
import com.nivesh.production.utils.niveshProperty.AdvanceDrawerLayout;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

import static com.nivesh.production.utils.Utils_Functions.showLog;

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context mContext;

    @BindView(R.id.adl_drawer)
    AdvanceDrawerLayout adl_drawer;

    Fragment newFragment;

    @BindView(R.id.nv_base_drawer)
    public NavigationView nv_base_drawer;

    @BindView(R.id.dl_toolbar)
    public Toolbar dl_toolbar;

    FragmentTransaction fragmentTransaction;
    LinearLayout ll_container_account_switch;
    ImageView iv_nav_login_type;
    View headerview;
    boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.base_drawer_activity);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        setSupportActionBar(dl_toolbar);
        if (savedInstanceState == null) {
            newFragment = new DashBoard_DLFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.dl_content_frame, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, adl_drawer, dl_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /* Called when a drawer has settled in a completely closed state. */
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                showLog("Drawer_Closed", "Close_Full");
                isOpen = false;
            }

            /* Called when a drawer has settled in a completely open state. */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                showLog("Drawer_Opened", "Open_Full");
                isOpen = true;
            }

            /* Called when a drawer has settled in a sliding state. */
//            @Override
//            public void onDrawerSlide(View drawerView,float slideOffset){
//                super.onDrawerSlide(drawerView,slideOffset);
//                if(!isOpen){
//                    showLog("Drawer_Slide", "Close_Full", "", "");
//                }
//                if(isOpen){
//                    showLog("Drawer_Slide", "Open_Full", "", "");
//                }
//            }

            /* Called when a drawer has settled in a completely state change */
//            @Override
//            public void onDrawerStateChanged(int newState) {
//                super.onDrawerStateChanged(newState);
//                showLog("Drawer_StateChanged", String.valueOf(newState), "", "");
//
//            }
        };


        adl_drawer.setViewScale(Gravity.START, 0.9f);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            showLog("MobileOS_21__<___", String.valueOf(Build.VERSION.SDK_INT));
            adl_drawer.setRadius(Gravity.START, 0); // this will protect showing rounded corners for prelollipop devices
        } else {
            showLog("MobileOS_21__>___", String.valueOf(Build.VERSION.SDK_INT));
            adl_drawer.setRadius(Gravity.START, 35);
        }
        adl_drawer.setViewElevation(Gravity.START, 20);
        adl_drawer.setDrawerListener(toggle);
        toggle.syncState();


        nv_base_drawer.setNavigationItemSelectedListener(this);
        drawerMainClick();
        disableNavigationViewScrollbars(nv_base_drawer); // hide vertical scrollbar showing in navigation bar

        nv_base_drawer.setCheckedItem(R.id.nav_menu_dashboard);   // set defualt highlighted item in navigation drawer during landing time first time

    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
//        Bundle bundle = new Bundle();

        switch (item.getItemId()) {
            case R.id.nav_menu_dashboard:
                fragment = new DashBoard_DLFragment();

                break;
            case R.id.nav_menu_clients:
                fragment = new Clients_DLFragment();

                break;
            case R.id.nav_menu_marketing:
                fragment = new Marketing_DLFragment();

                break;
            case R.id.nav_menu_daily_transections:
                fragment = new DailyTransections_DLFragment();

                break;
            case R.id.nav_menu_sip_decline:
                fragment = new SipDecline_DLFragment();

                break;
            case R.id.nav_menu_alerts:
                fragment = new Alerts_DLFragment();

                break;
            case R.id.nav_menu_view_orders:
                fragment = new ViewOrders_DLFragment();

                break;
            case R.id.nav_menu_content:
                fragment = new Contents_DLFragment();

                break;
            case R.id.nav_menu_language_switch:
                fragment = new LanguageSwitch_DLFragment();

                break;
            case R.id.nav_menu_change_password:
                fragment = new ChangePasword_DLFragment();

                break;
            case R.id.nav_menu_contact:
                fragment = new Contact_DLFragment();

                break;
            case R.id.nav_menu_sip_detail:
                fragment = new SipDetail_DLFragment();

                break;
            case R.id.nav_menu_exit:
                fragment = new Exit_DLFragment();

                break;
        }


//        if (id == R.id.nav_menu_dashboard) {
//            fragment = new DashBoard_DLFragment();
//
//        } else if (id == R.id.nav_menu_clients) {
//            fragment = new Clients_DLFragment();
//
//        } else if (id == R.id.nav_menu_marketing) {
//            fragment = new Marketing_DLFragment();
//
//        } else if (id == R.id.nav_menu_daily_transections) {
//            fragment = new DailyTransections_DLFragment();
//
//        } else if (id == R.id.nav_menu_sip_decline) {
//            fragment = new SipDecline_DLFragment();
//
//        } else if (id == R.id.nav_menu_alerts) {
//            fragment = new Alerts_DLFragment();
//
//        } else if (id == R.id.nav_menu_view_orders) {
//            fragment = new ViewOrders_DLFragment();
//
//        } else if (id == R.id.nav_menu_content) {
//            fragment = new Contents_DLFragment();
//
//        } else if (id == R.id.nav_menu_language_switch) {
//            fragment = new LanguageSwitch_DLFragment();
//
//        } else if (id == R.id.nav_menu_change_password) {
//            fragment = new ChangePasword_DLFragment();
//
//        } else if (id == R.id.nav_menu_contact) {
//            fragment = new Contact_DLFragment();
//
//        } else if (id == R.id.nav_menu_sip_detail) {
//            fragment = new SipDetail_DLFragment();
//
//        } else if (id == R.id.nav_menu_exit) {
//            fragment = new Exit_DLFragment();
//
//        }
//        else if (id == R.id.fb) {
//            bundle.putString("url", "https://www.facebook.com/androidhungerAH");
//            fragment = new WebViewFragment();
//            fragment.setArguments(bundle);
//        } else if (id == R.id.gplus) {
//            bundle.putString("url", "https://plus.google.com/+Androidhunger");
//            fragment = new WebViewFragment();
//            fragment.setArguments(bundle);
//        } else if (id == R.id.twitter) {
//            bundle.putString("url", "https://www.twitter.com/android_hunger");
//            fragment = new WebViewFragment();
//            fragment.setArguments(bundle);
//        } else if (id == R.id.github) {
//            bundle.putString("url", "https://github.com/avinashn/androidhunger.com");
//            fragment = new WebViewFragment();
//            fragment.setArguments(bundle);
//        } else if (id == R.id.youtube) {
//            bundle.putString("url", "https://www.youtube.com/androidhunger");
//            fragment = new WebViewFragment();
//            fragment.setArguments(bundle);
//        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.dl_content_frame, fragment);
            ft.commit();
        }
        adl_drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void drawerMainClick() {

        headerview = nv_base_drawer.getHeaderView(0);  // this will enable header view items
        ll_container_account_switch = (LinearLayout) headerview.findViewById(R.id.ll_container_account_switch);
        iv_nav_login_type = (ImageView) headerview.findViewById(R.id.iv_nav_login_type);


        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog("Header_", "Container_Click");
//                isDrawer();
            }
        });

        ll_container_account_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog("Header_", "Switch_Click");
                adl_drawer.closeDrawer(GravityCompat.START);
//                isDrawer();


            }
        });

        iv_nav_login_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog("Header_", "AccountType_Click");
                adl_drawer.closeDrawer(GravityCompat.START);
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (adl_drawer.isDrawerOpen(GravityCompat.START)) {
            adl_drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            finish();
        }
    }


    private void getSubBokerDropDownData() {
//        showLog("Create_Referrer_Activity ", "getSubBokerDropDownData_API-> " + GET_SUBBROKER_DROPDOWN_DATA, "", "");
//
//        pb_create_referrer_global.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.get("URL")
                .setPriority(Priority.MEDIUM)
//                .setOkHttpClient(new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS).build())
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                    }
                })
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        pb_create_referrer_global.setVisibility(View.GONE);
//                        showLog("Raw_Response_All", String.valueOf(response.toString()), "", "");
//                        parseSubBokerDropDownData(response);

                    }

                    @Override
                    public void onError(ANError anError) {
//                        pb_create_referrer_global.setVisibility(View.GONE);
//
//                        showLog("Raw_Response_Error ", anError.getMessage(), "", "");
                    }

                });
    }


}
