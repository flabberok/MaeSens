package by.maesens.android.account;

import android.os.Handler;
import android.os.Looper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Paul on 16.01.2016.
 */
public class MainThreadBus extends EventBus {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}
