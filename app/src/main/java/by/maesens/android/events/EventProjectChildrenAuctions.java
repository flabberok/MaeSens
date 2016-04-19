package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import retrofit2.Call;

/**
 * Created by Sol on 20.03.2016.
 */
public class EventProjectChildrenAuctions extends EventAuctions {
    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return  api.getProjectChildrenAuction(params[0]);
    }
}
