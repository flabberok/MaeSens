package by.maesens.android.account;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.ResponseLogIn;
import by.maesens.android.ui.fragments.LoginForm;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Павел on 11.04.2016.
 */
public class IntentServiceHelper extends IntentService {


    public IntentServiceHelper() {
        super(IntentServiceHelper.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String userName = intent.getStringExtra(LoginForm.MAESENS_USERNAME);
        String password = intent.getStringExtra(LoginForm.MAESENS_PASSWORD);

        IApiService myApi = ServiceHelper.getInstance().getRetrofit().create(IApiService.class);
        myApi.basicLogin(userName, password);

        Call<ResponseLogIn> call = myApi.basicLogin(userName, password);

        try {
            Response<ResponseLogIn> results = call.execute();

            Log.d(LoginForm.class.getName(), "UserID is " + results.body().getUserId());
            Log.d(LoginForm.class.getName(), "Token is " + results.body().getToken());

            String token = results.body().getToken();
            ServiceHelper.getBus().post(token);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
