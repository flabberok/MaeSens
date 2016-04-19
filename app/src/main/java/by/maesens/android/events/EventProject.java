package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseProject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Sol on 19.03.2016.
 */
public class EventProject extends BaseEvent {
    ResponseProject mResponseProject;

    @Override
    public IBaseResponse getResponse() {
        return mResponseProject;
    }

    @Override
    public void setResponse(Response response) {
        mResponseProject = (ResponseProject) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getProject(params[0], token);
    }
}
