package by.maesens.android.ui.tabs.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import by.maesens.android.R;
import by.maesens.android.events.EventSettingsNotification;
import by.maesens.android.model.settings.SettingsNotification;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseSettingsNotification;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;

/**
 * Created by Никита on 30.03.2016.
 */
public class TabSettingsNotifications extends BaseNetworkFragment<EventSettingsNotification> {

    private CheckBox mCbSuccessCreate;
    private CheckBox mCbBeatenBids;
    private CheckBox mCbNewBids;
    private CheckBox mCbEndedProjects;
    private CheckBox mCbBeforeDay;
    private CheckBox mCbBeforeHour;
    private CheckBox mCbUnreadMessages;
    private CheckBox mCbBuyAuc;
    private CheckBox mCbNewAuc;
    private Button mBtnUpdate;
    private Button mBtnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_notifications, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mCbSuccessCreate = (CheckBox) view.findViewById(R.id.notifSuccessCreate);
        mCbSuccessCreate.setChecked(true);
        mCbBeatenBids = (CheckBox) view.findViewById(R.id.notifBeatenBids);
        mCbBeatenBids.setChecked(true);
        mCbNewBids = (CheckBox) view.findViewById(R.id.notifNewBids);
        mCbNewBids.setChecked(true);
        mCbEndedProjects = (CheckBox) view.findViewById(R.id.notifEndedProjects);
        mCbEndedProjects.setChecked(true);
        mCbBeforeDay = (CheckBox) view.findViewById(R.id.notifBeforeDay);
        mCbBeforeDay.setChecked(true);
        mCbBeforeHour = (CheckBox) view.findViewById(R.id.notifBeforeHour);
        mCbBeforeHour.setChecked(true);
        mCbUnreadMessages = (CheckBox) view.findViewById(R.id.notifUnreadMessages);
        mCbUnreadMessages.setChecked(true);
        mCbBuyAuc = (CheckBox) view.findViewById(R.id.notifBuyAuc);
        mCbBuyAuc.setChecked(true);
        mCbNewAuc = (CheckBox) view.findViewById(R.id.notifNewAuc);
        mCbNewAuc.setChecked(true);

        mBtnUpdate = (Button) view.findViewById(R.id.btnUpdateUserMain);
        mBtnCancel = (Button) view.findViewById(R.id.btnUpdateCancel);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSettings();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void updateSettings() {
        List<String> settings = new ArrayList<>();
        if (!mCbSuccessCreate.isChecked()) {
            settings.add("1");
        }
        if (!mCbBeatenBids.isChecked()) {
            settings.add("2");
        }
        if (!mCbNewBids.isChecked()) {
            settings.add("6");
        }
        if (!mCbEndedProjects.isChecked()) {
            settings.add("9");
        }
        if (!mCbBeforeDay.isChecked()) {
            settings.add("12");
        }
        if (!mCbBeforeHour.isChecked()) {
            settings.add("13");
        }
        if (!mCbUnreadMessages.isChecked()) {
            settings.add("14");
        }
        if (!mCbBuyAuc.isChecked()) {
            settings.add("15");
        }
        if (!mCbNewAuc.isChecked()) {
            settings.add("16");
        }
        SettingsNotification settingsNotification = new SettingsNotification();
        settingsNotification.setSettings(settings);

        EventSettingsNotification event = new EventSettingsNotification();
        event.setSettings(settingsNotification);
        try {
            ServiceHelper.getInstance().getData(new String[]{}, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {

    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("!!!!", "onSuccessResponse");
        List<String> list = ((ResponseSettingsNotification) response).getResult();
        if (list != null) {
            if (list.contains("1")) {
                mCbSuccessCreate.setChecked(false);
            }
            if (list.contains("2")) {
                mCbBeatenBids.setChecked(false);
            }
            if (list.contains("6")) {
                mCbNewBids.setChecked(false);
            }
            if (list.contains("9")) {
                mCbEndedProjects.setChecked(false);
            }
            if (list.contains("12")) {
                mCbBeforeDay.setChecked(false);
            }
            if (list.contains("13")) {
                mCbBeforeHour.setChecked(false);
            }
            if (list.contains("14")) {
                mCbUnreadMessages.setChecked(false);
            }
            if (list.contains("15")) {
                mCbBuyAuc.setChecked(false);
            }
            if (list.contains("16")) {
                mCbNewAuc.setChecked(false);
            }
        }
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventSettingsNotification event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventSettingsNotification eventSettingsNotification) {
        super.onApiResponse(eventSettingsNotification);
        turnOnControls();
    }

    @Override
    protected String[] getApiParams() {
        return new String[0];
    }

    @Override
    public EventSettingsNotification getEvent() {
        return new EventSettingsNotification();
    }
}
