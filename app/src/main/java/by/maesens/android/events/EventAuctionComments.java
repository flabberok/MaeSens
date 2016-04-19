package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseComments;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Павел on 17.03.2016.
 */
public class EventAuctionComments extends EventWithList {

    private ResponseComments mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseComments) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionComments(params[0]);
    }
}
