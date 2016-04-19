package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctionInfo;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Виктор on 24.02.2016.
 */
public class EventAuctionInfo extends BaseEvent {

    private ResponseAuctionInfo mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseAuctionInfo) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuction(params[0]);
    }
}
