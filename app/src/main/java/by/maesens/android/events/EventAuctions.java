package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 16.02.2016.
 */
public class EventAuctions extends EventWithList {

    private ResponseAuctions mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        if (response != null) {
            mResponse = (ResponseAuctions) response.body();
        }
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {

        if(params.length == 1){
            return api.getAuctionsUnique(params[0]);
        }

        return api.getAuctionsSearch(params[0], params[1], params[2], params[3], params[4], params[5]);
    }
}
