package by.maesens.android.events;

import by.maesens.android.model.RequestBid;
import by.maesens.android.model.settings.SettingsNotification;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseBid;
import by.maesens.android.network.responses.ResponseSettingsNotification;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Виктор on 02.04.2016.
 */
public class EventBid extends BaseEvent {

    private ResponseBid mResponse;
    private RequestBid mNewBid;

    public RequestBid getNewBid() {
        return mNewBid;
    }

    public void setNewBid(RequestBid newBid) {
        mNewBid = newBid;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseBid) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        if (mNewBid != null)
            return api.doBid(token, params[0], mNewBid);
        else
            return null;
    }
}
