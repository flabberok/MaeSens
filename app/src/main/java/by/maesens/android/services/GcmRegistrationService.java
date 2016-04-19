package by.maesens.android.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import by.maesens.android.utils.GcmTokenManager;

/**
 * Created by Никита on 13.04.2016.
 */
public class GcmRegistrationService extends IntentService {

    public GcmRegistrationService() {
        super("GcmRegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("GcmRegistrationService", "Handling intent");
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            token = instanceID.getToken(GcmTokenManager.PROJECT_NUMBER, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GcmTokenManager.saveToken(getApplicationContext(), token);
        GcmTokenManager.sendTokenToServer(token);
    }
}
