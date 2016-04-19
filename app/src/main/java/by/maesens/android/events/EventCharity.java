package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseCharity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Миша on 24.02.2016.
 */
public class EventCharity extends EventWithList{

    private ResponseCharity mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseCharity) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getCharity(params[0],params[1],params[2]);
    }
}
