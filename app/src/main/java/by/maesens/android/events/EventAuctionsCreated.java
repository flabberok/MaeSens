package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctionsCreated;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 18.04.2016.
 */
public class EventAuctionsCreated extends EventWithList {
    private ResponseAuctionsCreated mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseAuctionsCreated) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionsCreated(token, params[0]);
    }
}
