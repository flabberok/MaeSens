package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseOrder;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Sol on 04.04.2016.
 */
public class EventProjectStatistics extends EventWithList {

    ResponseOrder mResponseOrder;

    @Override
    public IBaseResponse getResponse() {
        return mResponseOrder;
    }

    @Override
    public void setResponse(Response response) {
        mResponseOrder = (ResponseOrder) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getProjectStatistics(params[0],params[1], token);
    }
}
