package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseMyBids;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 29.03.2016.
 */
public class EventMyBids extends EventWithList {

    private ResponseMyBids mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseMyBids) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getMyBids(token);
    }
}
