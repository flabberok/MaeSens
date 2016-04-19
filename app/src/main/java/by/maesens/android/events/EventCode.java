package by.maesens.android.events;

import by.maesens.android.model.settings.SettingsCode;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseCode;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 03.04.2016.
 */
public class EventCode extends BaseEvent {

    private ResponseCode mResponseCode;
    private SettingsCode mSettingsCode;

    public SettingsCode getSettingsCode() {
        return mSettingsCode;
    }

    public void setSettingsCode(SettingsCode settingsCode) {
        mSettingsCode = settingsCode;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponseCode;
    }

    @Override
    public void setResponse(Response response) {
        if (response != null){
            mResponseCode = (ResponseCode) response.body();
        }
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.checkCode(token, mSettingsCode);
    }
}
