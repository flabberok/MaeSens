package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseUser;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 24.03.2016.
 */
public class EventUser extends BaseEvent {

    private ResponseUser mResponseUser;

    @Override
    public IBaseResponse getResponse() {
        return mResponseUser;
    }

    @Override
    public void setResponse(Response response) {
        mResponseUser = (ResponseUser) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getUser(token, params[0]);
    }
}
