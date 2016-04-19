package by.maesens.android.events;

import by.maesens.android.network.api.IApiService;
import by.maesens.android.network.responses.IBaseResponse;
import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseEvent {
    protected boolean isSuccess = true;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public abstract IBaseResponse getResponse();

    public abstract void setResponse(Response response);

    public abstract Call getCallApi(IApiService api, String[] params, String token);
}
