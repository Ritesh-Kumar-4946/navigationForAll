package com.nivesh.production.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.nivesh.production.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utils_Functions {


    /* this function show toast if isTOAST is true (Start)*/
//    public static final boolean isTOAST = true;
    public static final boolean isTOAST = false;

    public static void showToastShort(Context mcontext, CharSequence strToast) {
        if (isTOAST) {
            Toast.makeText(mcontext, strToast, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToastLong(Context mcontext, CharSequence strToast) {
        if (isTOAST) {
            Toast.makeText(mcontext, strToast, Toast.LENGTH_LONG).show();
        }
    }
    /* this function show toast if isTOAST is true (End)*/


    /* this function show log if isLOG is true (Start)*/
    public static final boolean isLOG = true;

    //    public static final boolean isLOG = false;
//    public static void showLog(String strLog_Tag, String strLog_Msg1, String strLog_Msg2, String strLog_Msg3) {
//        if (isLOG) {
//            if (!strLog_Msg1.equalsIgnoreCase("")
//                    && strLog_Msg2.equalsIgnoreCase("")
//                    && strLog_Msg3.equalsIgnoreCase("")) {
//
//                Log.e(strLog_Tag, strLog_Msg1);
//
//            } else if (!strLog_Msg1.equalsIgnoreCase("")
//                    && !strLog_Msg2.equalsIgnoreCase("")
//                    && strLog_Msg3.equalsIgnoreCase("")) {
//
//                Log.e(strLog_Tag, strLog_Msg1 + "   " + strLog_Msg2);
//
//            } else if (!strLog_Msg1.equalsIgnoreCase("")
//                    && !strLog_Msg2.equalsIgnoreCase("")
//                    && !strLog_Msg3.equalsIgnoreCase("")) {
//
//                Log.e(strLog_Tag, strLog_Msg1 + "   " + strLog_Msg2 + "     " + strLog_Msg3);
//
//            } else if (!strLog_Msg1.equalsIgnoreCase("")
//                    && strLog_Msg2.equalsIgnoreCase("")
//                    && !strLog_Msg3.equalsIgnoreCase("")) {
//
//                Log.e(strLog_Tag, strLog_Msg1 + "   " + strLog_Msg2 + "     " + strLog_Msg3);
//
//            }
//
//        }
//    }

    public static void showLog(String strLog_Tag, String strLog_Msg){
        if (isLOG) {
            Log.e(strLog_Tag, strLog_Msg);
        }
    }
    /* this function show log if isLOG is true (End)*/


    /* this function is used for check is internet is available or not (Start)*/
    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
//            Utility.showDialogValidation(activity, activity.getString(R.string.error_network));
//            Toast.makeText(context.getApplicationContext(), Constants.ERROR_MSG_NETWORK_NOT_AVAILABLE, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    /* this function is used for check is internet is available or not (End)*/


    /*this function is used for check is internet is working properly (Start)*/
    public static class InternetCheck extends AsyncTask<Void, Void, Boolean> {

        private Consumer mConsumer;

        public interface Consumer {
            void accept(Boolean internet);
        }

        public InternetCheck(Consumer consumer) {
            mConsumer = consumer;
            execute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean internet) {
            mConsumer.accept(internet);
        }
    }
    /*this function is used for check is internet is working properly (End)*/


    /*this function is used for getting internet source type (Start)*/
    public static String getNetworkClass(Context context) {
        /*https://stackoverflow.com/questions/2802472/detect-network-connection-type-on-android*/
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; //not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:  //api<25 : replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:  //api<25 : replace by 18
                case 19:  //LTE_CA
                    return "4G";
                default:
                    return "?";
            }
        }
        return "?";
    }
    /*this function is used for getting internet source type (End)*/


    /*this function is used for making ativity full screen (Start)*/
    public static void fullScreen(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /*this function is used for making ativity full screen (End)*/

}
