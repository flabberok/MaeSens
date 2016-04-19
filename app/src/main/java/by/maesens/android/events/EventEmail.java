package by.maesens.android.events;

import by.maesens.android.model.settings.SettingsEmail;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseEmail;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 03.04.2016.
 */
public class EventEmail extends BaseEvent {

    private ResponseEmail mResponseEmail;
    private SettingsEmail mEmail;

    public SettingsEmail getEmail() {
        return mEmail;
    }

    public void setEmail(SettingsEmail email) {
        mEmail = email;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponseEmail;
    }

    @Override
    public void setResponse(Response response) {
        mResponseEmail = (ResponseEmail) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.updateEmail(token, mEmail);
    }
}
