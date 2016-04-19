package by.maesens.android.services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Никита on 13.04.2016.
 */
public class MaesensInstanceIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GcmRegistrationService.class);
        startService(intent);
    }
}
