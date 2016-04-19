package by.maesens.android.events;

import by.maesens.android.model.settings.SettingsNotification;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseSettingsNotification;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventSettingsNotification extends BaseEvent {

    private ResponseSettingsNotification mResponse;
    private SettingsNotification mSettings;

    public SettingsNotification getSettings() {
        return mSettings;
    }

    public void setSettings(SettingsNotification settings) {
        mSettings = settings;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseSettingsNotification) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        if (mSettings == null) {
            return api.getNotificationSettings(token);
        } else {
            return api.updateNotificationSettings(token, mSettings);
        }
    }
}
