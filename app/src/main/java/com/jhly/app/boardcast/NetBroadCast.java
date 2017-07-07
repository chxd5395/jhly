package com.jhly.app.boardcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by r on 2017/4/29.
 */

public class NetBroadCast extends BroadcastReceiver {
    private static final String TAG = "xu";
    public static final String TAG1 = "xxx";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Log.i(TAG1, "CONNECTIVITY_ACTION");

            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        Log.e(TAG, "当前WiFi连接可用 ");
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        Log.e(TAG, "当前移动网络连接可用 ");
                        Toast.makeText(context,"当前正使用移动网络",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");

                }
                Log.e(TAG1, "info.getTypeName()" + activeNetwork.getTypeName());
                Log.e(TAG1, "getSubtypeName()" + activeNetwork.getSubtypeName());
                Log.e(TAG1, "getState()" + activeNetwork.getState());
                Log.e(TAG1, "getDetailedState()"
                        + activeNetwork.getDetailedState().name());
                Log.e(TAG1, "getDetailedState()" + activeNetwork.getExtraInfo());
                Log.e(TAG1, "getType()" + activeNetwork.getType());
            } else {   // not connected to the internet
                Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                Toast.makeText(context,"没有网络了哦~",Toast.LENGTH_SHORT).show();
            }


        }

    }
}
