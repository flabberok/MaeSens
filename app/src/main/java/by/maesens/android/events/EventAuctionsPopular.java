package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponsePopularAuctions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 13.02.2016.
 */
public class EventAuctionsPopular extends BaseEvent{

    private ResponsePopularAuctions mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponsePopularAuctions) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionsPopular();
    }
}
