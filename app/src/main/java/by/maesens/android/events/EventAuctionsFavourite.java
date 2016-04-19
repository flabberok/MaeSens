package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctionsFavourite;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 14.02.2016.
 */
public class EventAuctionsFavourite extends EventWithList {

    private ResponseAuctionsFavourite mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseAuctionsFavourite) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionsFavourite(token);
    }
}
