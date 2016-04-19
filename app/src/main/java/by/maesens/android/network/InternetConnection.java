package by.maesens.android.network;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import by.maesens.android.R;

/**
 * Created by Igor Paliashchuk on 29.02.16.
 */
public class InternetConnection {

    public static boolean isAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                if (isServerOnline()){
                    return true;
                }
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (isServerOnline()){
                    return true;
                }
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIMAX) {
                if (isServerOnline()){
                    return true;
                }
            }
        } else {
            showSnackBar(context);
        }
        return false;
    }

    private static boolean isServerOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 178.172.236.60");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void showSnackBar(Context context){
        View parent = ((Activity) context).getWindow()
                .getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parent,
                context.getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
