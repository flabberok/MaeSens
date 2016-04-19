package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Auction;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseProfileList;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 16.03.2016.
 */
public class EventProfileList extends BaseEvent {

    private ResponseProfileList mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = new ResponseProfileList((List<Auction>) response.body());
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getProfileList(token, params[0]);
    }
}
