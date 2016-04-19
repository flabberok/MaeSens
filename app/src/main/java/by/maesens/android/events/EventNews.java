package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseNews;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 18.03.2016.
 */
public class EventNews extends EventWithList {

    private ResponseNews mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        mResponse = (ResponseNews)response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getNews(token, params[0]);
    }
}
