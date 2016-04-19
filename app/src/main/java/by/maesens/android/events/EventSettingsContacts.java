package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseSettingsContacts;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventSettingsContacts extends BaseEvent {

    private ResponseSettingsContacts mResponseContacts;

    @Override
    public IBaseResponse getResponse() {
        return mResponseContacts;
    }

    @Override
    public void setResponse(Response response) {
        mResponseContacts = (ResponseSettingsContacts) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getSettingsContacts(token);
    }
}
