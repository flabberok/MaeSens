package by.maesens.android.events;

import by.maesens.android.model.settings.SettingsPersonal;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseSettingsPersonal;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventSettingsPersonal extends BaseEvent {

    private ResponseSettingsPersonal mResponseSettingsPersonal;
    private SettingsPersonal mSettingsPersonal;

    public SettingsPersonal getSettingsPersonal() {
        return mSettingsPersonal;
    }

    public void setSettingsPersonal(SettingsPersonal settingsPersonal) {
        mSettingsPersonal = settingsPersonal;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponseSettingsPersonal;
    }

    @Override
    public void setResponse(Response response) {
        mResponseSettingsPersonal = (ResponseSettingsPersonal) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        if (mSettingsPersonal == null){
            return api.getSettingsPersonal(token);
        } else {
            return api.updateSettingsPersonal(token, mSettingsPersonal);
        }
    }
}
