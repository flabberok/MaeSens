package by.maesens.android.events;

import java.util.List;

import by.maesens.android.model.Category;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseCharityCategories;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Sol on 09.03.2016.
 */
public class EventProjectsCategory extends BaseEvent {
    private ResponseCharityCategories mResponseCharityCategories;

    @Override
    public IBaseResponse getResponse() {
        return mResponseCharityCategories;
    }

    @Override
    public void setResponse(Response response) {
        mResponseCharityCategories = new ResponseCharityCategories();
        mResponseCharityCategories.setList((List<Category>) response.body());
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        return api.getCharityCategory();
    }
}
