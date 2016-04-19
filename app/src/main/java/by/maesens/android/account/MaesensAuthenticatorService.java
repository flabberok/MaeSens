package by.maesens.android.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import by.maesens.android.ui.fragments.LoginForm;

/**
 * Created by Павел on 11.04.2016.
 */
public class MaesensAuthenticatorService extends Service {

    private MaesensAuthenticator mAuthenticator;


    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new MaesensAuthenticator(getApplicationContext());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Login","MaesensAuthenticatorService onBind");
        return mAuthenticator.getIBinder();
    }
}
