package by.maesens.android.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Никита on 13.04.2016.
 */
public class GcmTokenManager {
    public static final String PROJECT_NUMBER = "199809485180";
    private static final String PREFS_NAME = "test_prefs";
    private static final String KEY_TOKEN = "token_key";

    public static void saveToken(Context context, String token) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString(KEY_TOKEN, token).commit();
        Log.d("GcmTokenManager", "Token saved to prefs: " + token);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void sendTokenToServer (String token){
        //отправляем токен на сервер маесенс
    }
}
