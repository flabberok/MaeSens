package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseProfile;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 16.03.2016.
 */
public class EventProfile extends BaseEvent {

    private ResponseProfile mResponseProfile;

    @Override
    public IBaseResponse getResponse() {
        return mResponseProfile;
    }

    @Override
    public void setResponse(Response response) {
        mResponseProfile = (ResponseProfile) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getProfile(token);
    }
}
