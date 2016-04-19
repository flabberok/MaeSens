package by.maesens.android.ui.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import by.maesens.android.events.BaseEvent;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.interfaces.IControlsTrigger;
import by.maesens.android.loaders.PicassoLoader;
import by.maesens.android.network.responses.IBaseResponse;

/**
 * Every child class of BaseNetworkFragment should @Override   and add @Subscribe annotation with
 * method onApiResponse to receive necessary events.
 * At the first line of the method you only need to call super method with event.
 */
public abstract class BaseNetworkFragment<EVENT extends BaseEvent> extends Fragment {

    protected IControlsTrigger mControlsTrigger;
    protected Picasso mPicasso;
    protected IComponentSetter mFragmentSetter;
    protected int mRequestCounter = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            init();
        } else
        {
            loadDataFromBundle(savedInstanceState);

        }
    }

    protected abstract void loadDataFromBundle(Bundle savedInstanceState);

    @Override
    public void onAttach(Context activity) {
        mPicasso = PicassoLoader.getInstance(activity);
        super.onAttach(activity);
        if (activity instanceof IControlsTrigger) {
            mControlsTrigger = (IControlsTrigger) activity;
        }
        if (activity instanceof IComponentSetter) {
            mFragmentSetter = (IComponentSetter) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mControlsTrigger = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Every child class of BaseNetworkFragment should @Override this method and add @Subscribe annotation with
     * @param event to receive necessary events.
     * At the first line of the method you only need to call super method with @param event.
     */
    public void onApiResponse(EVENT event) {

        if (event.isSuccess()) {
            onSuccessResponse(event.getResponse());
        } else {
            onFailResponse(event.getResponse());
        }
        turnOnControls();
    }

    protected void turnOnControls(){
        decRequestCounter();
        if(mRequestCounter == 0){
            enableControls();
            if(mControlsTrigger != null){
                mControlsTrigger.enableControls();
            }
        }
    }

    protected void turnOffControls(){
        incRequestCounter();
        disableControls();
        if(mControlsTrigger != null){
            mControlsTrigger.disableControls();
        }
    }

    protected void init() {
        sendApiRequest();
    }

    abstract public void onFailResponse(IBaseResponse response);

    abstract public void onSuccessResponse(IBaseResponse response);

    abstract public void disableControls();

    abstract public void enableControls();

    abstract public void sendApiRequest(String[] paramsForAPI,EVENT event);

    public void sendApiRequest() {
        sendApiRequest( getApiParams(), getEvent());
    }

    private void decRequestCounter(){
        mRequestCounter--;
        if(mRequestCounter < 0)
            mRequestCounter = 0;
        Log.d("BaseNetworkFragment", "take mRequestCounter = " + mRequestCounter);
    }

    private void incRequestCounter(){
        mRequestCounter++;
        Log.d("BaseNetworkFragment", "send mRequestCounter = " + mRequestCounter);
    }
    protected abstract String[] getApiParams();
    abstract public EVENT getEvent();
}
