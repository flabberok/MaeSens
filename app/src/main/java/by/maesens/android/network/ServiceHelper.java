package by.maesens.android.network;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import by.maesens.android.account.AppAccount;
import by.maesens.android.account.MainThreadBus;
import by.maesens.android.events.BaseEvent;
import by.maesens.android.network.api.IApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceHelper {

    private static ServiceHelper ourInstance = new ServiceHelper();
    private Retrofit mRetrofit;
    private static final MainThreadBus bus = new MainThreadBus();

    //public final static String URLServer = "https://staging.tvojmir.com/";
    public final static String URLServer = "https://maesens.by/";

    public static ServiceHelper getInstance() {
        return ourInstance;
    }

    private ServiceHelper() {
    }

    public Retrofit getRetrofit() {
        if (mRetrofit != null) return mRetrofit;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLServer)
                .client(TrustedHttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

    public static EventBus getBus() {
        return bus;
    }

    public void getData(String[] params,
                        final BaseEvent event, Context context)
            throws Exception {

        IApiService api = getRetrofit().create(IApiService.class);
        Call call = event.getCallApi(api, params, AppAccount.getToken());

        Log.d("ServiceHelper", "event class == " + event.getClass().getSimpleName());

        if (InternetConnection.isAvailable(context)) {
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("ServiceHelper", "isSuccess " + response.body().toString());
                        sendEvent(response, true);
                    } else {
                        Log.d("ServiceHelper", "NOT isSuccess");
                        Log.d("ServiceHelper", response.errorBody().toString());
                        Log.d("ServiceHelper", response.message());
                        Log.d("ServiceHelper", "NOT isSuccess");
                        Log.d("ServiceHelper", response.toString());
                        sendEvent(response, false);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d("ServiceHelper", "onFailure. " + t.getMessage());
                    sendEvent(null, false);
                }

                private void sendEvent(Response resp, boolean success) {
                    event.setResponse(resp);
                    event.setIsSuccess(success);
                    EventBus.getDefault().post(event);
                }
            });
        }
    }
}
