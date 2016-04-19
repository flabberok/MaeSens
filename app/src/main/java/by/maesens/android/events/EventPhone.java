package by.maesens.android.events;

import by.maesens.android.model.settings.SettingsPhone;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponsePhone;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 03.04.2016.
 */
public class EventPhone extends BaseEvent {

    private ResponsePhone mResponsePhone;
    private SettingsPhone mSettingsPhone;

    public SettingsPhone getSettingsPhone() {
        return mSettingsPhone;
    }

    public void setSettingsPhone(SettingsPhone settingsPhone) {
        mSettingsPhone = settingsPhone;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponsePhone;
    }

    @Override
    public void setResponse(Response response) {
        if(response != null){
            mResponsePhone = (ResponsePhone) response.body();
        }
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.verifyPhone(token, mSettingsPhone);
    }
}
