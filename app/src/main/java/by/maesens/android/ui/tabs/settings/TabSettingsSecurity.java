package by.maesens.android.ui.tabs.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.maesens.android.R;
import by.maesens.android.account.AppAccount;
import by.maesens.android.ui.dialogs.ChangeEmailDialog;
import by.maesens.android.events.EventAssociation;
import by.maesens.android.events.EventCode;
import by.maesens.android.events.EventEmail;
import by.maesens.android.events.EventPhone;
import by.maesens.android.events.EventProfile;
import by.maesens.android.events.EventSettingsContacts;
import by.maesens.android.model.Association;
import by.maesens.android.model.Profile;
import by.maesens.android.model.settings.SettingsCode;
import by.maesens.android.model.settings.SettingsContacts;
import by.maesens.android.model.settings.SettingsEmail;
import by.maesens.android.model.settings.SettingsPhone;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAssociation;
import by.maesens.android.network.responses.ResponseProfile;
import by.maesens.android.network.responses.ResponseSettingsContacts;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;

/**
 * Created by Никита on 30.03.2016.
 */
public class TabSettingsSecurity extends BaseNetworkFragment<EventSettingsContacts> {

    private static final String REFERENCE_CHANGE_PASSWORD = "https://maesens.by/password_change/";
    private static final String REFERENCE_LOGIN_VK = "https://www.vk.com/";
    private static final String REFERENCE_LOGIN_FB = "https://www.facebook.com/";
    public static final String VAR_NEW_EMAIL = "newEmail";
    public static final int DIALOG_FRAGMENT = 1;
    public static final int DIALOG_RESULT_OK = 0;

    private TextView mTvPhone;
    private TextView mTvEmail;
    private Button mBtnChangePhone;
    private Button mBtnChangeEmail;
    private ImageView mFbVerified;
    private ImageView mVkVerified;
    private ImageView mEmailVerified;
    private ImageView mPhoneVerified;
    private Button mBtnChainEmail;
    private Button mBtnChainPhone;
    private Button mBtnChainVk;
    private Button mBtnChainFb;
    private Button mBtnChangePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_security, container, false);
        initViews(view);
        try {
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(AppAccount.getId())},
                    new EventAssociation(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{},
                    new EventProfile(), getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initViews(View view) {
        mTvPhone = (TextView) view.findViewById(R.id.etPhone);
        mTvEmail = (TextView) view.findViewById(R.id.etEmail);
        mBtnChangePhone = (Button) view.findViewById(R.id.btnChangePhone);
        mBtnChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhone();
            }
        });
        mBtnChangeEmail = (Button) view.findViewById(R.id.btnChangeEmail);
        mBtnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });
        mFbVerified = (ImageView) view.findViewById(R.id.imgFbVerify);
        mVkVerified = (ImageView) view.findViewById(R.id.imgVkVerify);
        mEmailVerified = (ImageView) view.findViewById(R.id.imgEmailVerify);
        mPhoneVerified = (ImageView) view.findViewById(R.id.imgPhoneVerify);
        mBtnChainEmail = (Button) view.findViewById(R.id.btnChainEmail);
        mBtnChainEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });
        mBtnChainPhone = (Button) view.findViewById(R.id.btnChainPhone);
        mBtnChainPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhone();
            }
        });
        mBtnChainVk = (Button) view.findViewById(R.id.btnChainVk);
        mBtnChainVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chainVk();
            }
        });
        mBtnChainFb = (Button) view.findViewById(R.id.btnChainFb);
        mBtnChainFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chainFb();
            }
        });
        mBtnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
        mBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePhone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_phone, null);
        builder.setIcon(R.drawable.ic_call_24dp);
        builder.setTitle(R.string.phone_change_title);

        final EditText phoneEt = (EditText) view.findViewById(R.id.etPhone);
        final EditText codeEt = (EditText) view.findViewById(R.id.etCode);

        final Button sendPhone = (Button) view.findViewById(R.id.btnChangeNumber);
        sendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEt.setEnabled(true);
                phoneEt.setEnabled(false);
                SettingsPhone settingsPhone = new SettingsPhone();
                settingsPhone.setPhone(phoneEt.getText().toString());
                EventPhone eventPhone = new EventPhone();
                eventPhone.setSettingsPhone(settingsPhone);
                try {
                    Log.d("!!!", "send phone");
                    ServiceHelper.getInstance().getData(new String[]{}, eventPhone, getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sendPhone.setEnabled(false);

        final Button checkCode = (Button) view.findViewById(R.id.btnCheckCode);
        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsCode settingsCode = new SettingsCode();
                settingsCode.setPhone(phoneEt.getText().toString());
                settingsCode.setVerification_code(codeEt.getText().toString());
                EventCode eventCode = new EventCode();
                eventCode.setSettingsCode(settingsCode);
                try {
                    Log.d("!!!", "check code");
                    ServiceHelper.getInstance().getData(new String[]{}, eventCode, getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        checkCode.setEnabled(false);
        codeEt.setEnabled(false);
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // DO TASK
                    }
                });

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkPhone(s.toString())) {
                    sendPhone.setEnabled(true);
                } else {
                    sendPhone.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkCodeLength(s.toString())) {
                    checkCode.setEnabled(true);
                } else {
                    checkCode.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean checkCodeLength(String code){
        return code.length() == 3;
    }

    private boolean checkPhone(String phone){
        Pattern pattern = Pattern.compile("^(\\+375)(29|33|44|25)\\d{7}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private void changeEmail() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        ChangeEmailDialog dialog = new ChangeEmailDialog();
        dialog.setTargetFragment(this, DIALOG_FRAGMENT);
        dialog.show(ft, "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == DIALOG_RESULT_OK) {
                String email = data.getStringExtra(VAR_NEW_EMAIL);
                if (!isEmailValid(email)){
                    Toast.makeText(getActivity(), getString(R.string.incorrect_email), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("onActivityResult", "запрос на смену адреса");
                    EventEmail eventEmail = new EventEmail();
                    SettingsEmail settings = new SettingsEmail();
                    settings.setEmail(email);
                    eventEmail.setEmail(settings);
                    try {
                        ServiceHelper.getInstance().getData(new String[]{}, eventEmail, getContext());
                        turnOffControls();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void chainVk() {
        //TODO разобраться с механизмом привязки Контакта
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(REFERENCE_LOGIN_VK));
        startActivity(intent);
    }

    private void chainFb() {
        //TODO разобраться с механизмом привязки Facebook
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(REFERENCE_LOGIN_FB));
        startActivity(intent);
    }

    private void changePassword() {
        //TODO узнать какие параметры передавать при смене пароля
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(REFERENCE_CHANGE_PASSWORD));
        startActivity(intent);
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {

    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        SettingsContacts settingsContacts = ((ResponseSettingsContacts) response).getResult();
        mTvPhone.setText(settingsContacts.getPhone());
        mTvEmail.setText(settingsContacts.getEmail());
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventSettingsContacts event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String[] getApiParams() {
        return new String[0];
    }

    @Override
    public EventSettingsContacts getEvent() {
        return new EventSettingsContacts();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventSettingsContacts eventSettingsContacts) {
        super.onApiResponse(eventSettingsContacts);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAssociationResponse(EventAssociation eventAssociation) {
        List<Association> associations = ((ResponseAssociation) eventAssociation.getResponse()).getResult();
        verifyVkAndFb(associations);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEmailResponse(EventEmail eventEmail) {
        if (eventEmail.isSuccess()){
            Toast.makeText(getActivity(), getString(R.string.email_change_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), getString(R.string.email_change_error), Toast.LENGTH_SHORT).show();
        }
        turnOnControls();
    }

    private void verifyVkAndFb(List<Association> associations) {
        for (Association a : associations) {
            if (a.getProvider().equals("vk-oauth2")) {
                mVkVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
                mVkVerified.setVisibility(View.VISIBLE);
                mBtnChainVk.setVisibility(View.GONE);
            }
            if (a.getProvider().equals("facebook")) {
                mFbVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
                mFbVerified.setVisibility(View.VISIBLE);
                mBtnChainFb.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onProfileResponse(EventProfile eventProfile) {
        Profile profile = ((ResponseProfile) eventProfile.getResponse()).getResult();
        verifyEmailAndPhone(profile);
        turnOnControls();
    }

    private void verifyEmailAndPhone(Profile profile) {
        if (profile.isEmail_verified()) {
            mEmailVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
            mEmailVerified.setVisibility(View.VISIBLE);
            mBtnChainEmail.setVisibility(View.GONE);
        }
        if (profile.isPhone_verified()) {
            mPhoneVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
            mPhoneVerified.setVisibility(View.VISIBLE);
            mBtnChainPhone.setVisibility(View.GONE);
        }
    }
}
