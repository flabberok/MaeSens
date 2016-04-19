package by.maesens.android.events;

import by.maesens.android.model.Tag;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseTag;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 02.04.2016.
 */
public class EventTag extends BaseEvent {

    private ResponseTag mResponseTag;
    private Tag mTag;

    public Tag getTag() {
        return mTag;
    }

    public void setTag(Tag tag) {
        mTag = tag;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponseTag;
    }

    @Override
    public void setResponse(Response response) {
        mResponseTag = (ResponseTag) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.addTag(token, mTag);
    }
}
