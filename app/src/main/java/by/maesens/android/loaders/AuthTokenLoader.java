package by.maesens.android.loaders;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import by.maesens.android.R;

/**
 * Created by Павел on 02.03.2016.
 */
public class AuthTokenLoader {


//    private final String mObtainTokenUrl;
    private final String mLogin;
    private final String mPassword;
    private String mAuthToken;

    public AuthTokenLoader(Context context, String login, String password) {
//        super(context);
//        mObtainTokenUrl = context.getString(R.string.github_obtain_token_url, GitHubApp.CLIENT_ID);
        mLogin = login;
        mPassword = password;
    }

//    public static String signIn(Context context, String login, String password) {
//        try {
//            return new AuthTokenLoader(context, login, password).signIn();
//        } catch (IOException e) {
//            Log.e(AuthTokenLoader.class.getSimpleName(), e.getMessage(), e);
//        }
//        return null;
//    }


}
