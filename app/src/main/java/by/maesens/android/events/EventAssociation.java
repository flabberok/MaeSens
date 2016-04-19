package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Association;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAssociation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 24.03.2016.
 */
public class EventAssociation extends BaseEvent {

    private ResponseAssociation mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = new ResponseAssociation((List<Association>)response.body());
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAssociation(token, params[0]);
    }
}
