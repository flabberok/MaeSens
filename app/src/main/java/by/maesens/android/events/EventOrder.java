package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseOrder;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 16.03.2016.
 */
public class EventOrder extends EventWithList {

    private ResponseOrder mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseOrder) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getOrders(token);
    }
}
