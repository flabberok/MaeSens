package by.maesens.android.events;

import android.util.Log;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseLogIn;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Павел on 05.04.2016.
 */
public class EventLogIn extends BaseEvent {

    private ResponseLogIn mResponse;

    @Override
    public IBaseResponse getResponse() {
        return null;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseLogIn) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        Log.d("LogIn","EventLogIn");
        return api.basicLogin(params[0], params[1]);
    }
}
