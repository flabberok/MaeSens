package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Category;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseCategory;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 10.03.2016.
 */
public class EventCategory extends BaseEvent {

    private ResponseCategory mResponse;

    @Override
    public IBaseResponse getResponse() {
        return mResponse;
    }

    @Override
    public void setResponse(Response response) {
        if(response != null){
            mResponse = new ResponseCategory((List<Category>)response.body());
        }
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getAuctionCategory();
    }
}
