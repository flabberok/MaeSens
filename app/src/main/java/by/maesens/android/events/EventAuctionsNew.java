package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Auction;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseNewAuctions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 22.02.2016.
 */
public class EventAuctionsNew extends EventWithList {

    private ResponseNewAuctions mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = new ResponseNewAuctions((List<Auction>)response.body());
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionsNew();
    }
}
