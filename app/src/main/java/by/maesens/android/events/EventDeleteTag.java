package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventDeleteTag extends BaseEvent {
    @Override
    public IBaseResponse getResponse() {
        return null;
    }

    @Override
    public void setResponse(Response response) {

    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.deleteTag(token, params[0]);
    }
}
