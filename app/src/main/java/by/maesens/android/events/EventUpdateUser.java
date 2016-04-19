package by.maesens.android.events;

import by.maesens.android.model.User;
import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseUser;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Никита on 31.03.2016.
 */
public class EventUpdateUser extends BaseEvent {

    private ResponseUser mResponseUser;
    private User mUpdateUser;

    public User getUpdateUser() {
        return mUpdateUser;
    }

    public void setUpdateUser(User updateUser) {
        mUpdateUser = updateUser;
    }

    @Override
    public IBaseResponse getResponse() {
        return mResponseUser;
    }

    @Override
    public void setResponse(Response response) {
        mResponseUser = (ResponseUser) response.body();
    }

    @Override
    public Call getCallApi(IApiService api, String[] params, String token) {
        if (mUpdateUser == null){
            return api.getUserSettings(token);
        } else {
            return api.updateUser(token, mUpdateUser);
        }
    }
}
