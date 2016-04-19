package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Location;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseLocations;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 23.02.2016.
 */
public class EventLocations extends BaseEvent {

    private ResponseLocations mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        if (response != null) {
            mResponse = new ResponseLocations((List<Location>) response.body());
        }
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getLocations();
    }
}
