package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseDonation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 16.03.2016.
 */
public class EventDonation extends BaseEvent{

    private ResponseDonation mResponseDonation;

    @Override
    public IBaseResponse getResponse() {
        return mResponseDonation;
    }

    @Override
    public void setResponse(Response response) {
        mResponseDonation = (ResponseDonation) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getDonation(token, params[0]);
    }
}
