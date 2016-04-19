package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctionsWon;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 18.04.2016.
 */
public class EventAuctionsWon extends EventWithList {
    private ResponseAuctionsWon mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseAuctionsWon) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionsWon(token);
    }
}
