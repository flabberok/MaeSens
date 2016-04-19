package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Tag;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseTags;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventTags extends BaseEvent {

    private ResponseTags mResponseTags;

    @Override
    public IBaseResponse getResponse() {
        return mResponseTags;
    }

    @Override
    public void setResponse(Response response) {
        mResponseTags = new ResponseTags((List<Tag>) response.body());
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getTags(token);
    }
}
