package by.maesens.android.ui.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.Subscribe;

import by.maesens.android.R;
import by.maesens.android.account.IntentServiceHelper;
import by.maesens.android.account.MaesensAccount;
import by.maesens.android.model.LogIn;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.activity.LoginActivity;

public class LoginForm extends Fragment implements View.OnClickListener {

    private EditText mUserName;
    private EditText mPassword;
    private Button mLogInButton;
    private Button mSignInButton;
    private LoginButton mLogInButtonFacebook;
    private CallbackManager callbackManager;
    private LogIn mLogIn;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static final String MAESENS_USERNAME = "by.maesens.android.username";
    public static final String MAESENS_PASSWORD = "by.maesens.android.password";

    private final String username = "victorchuholskiy@gmail.com";
    private final String password = "11111";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        Log.d("LogIn", " LoginForm onCreate");
        ServiceHelper.getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_form, container, false);
        mUserName = (EditText) view.findViewById(R.id.login);
        mPassword = (EditText) view.findViewById(R.id.password);
        mLogInButton = (Button) view.findViewById(R.id.btn_log_in);
        mSignInButton = (Button) view.findViewById(R.id.btn_sign_in);
        mLogInButtonFacebook = (LoginButton) view.findViewById(R.id.login_button_facebook);
        mLogInButtonFacebook.setReadPermissions("user_friends");
        mLogInButtonFacebook.setFragment(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getActivity());
        mLogInButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        AppEventsLogger.deactivateApp(getActivity());
        mLogInButton.setOnClickListener(null);
        mSignInButton.setOnClickListener(null);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServiceHelper.getBus().unregister(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_log_in: {
                logIn();
            }
            break;
            case R.id.login_button_facebook: {
                logInFacebook();
            }
            break;
            case R.id.btn_sign_in: {
                signIn();
            }
            break;
        }

    }

    private void logIn() {
        if (TextUtils.isEmpty(mUserName.getText())) {
            mUserName.setError(getString(R.string.login));
        } else if (TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError(getString(R.string.hint_password));
        } else {
            Intent intentService = new Intent(getActivity(), IntentServiceHelper.class);
            intentService.putExtra(MAESENS_USERNAME, mUserName.getText().toString());
            intentService.putExtra(MAESENS_PASSWORD, mPassword.getText().toString());
            getActivity().startService(intentService);
        }

    }

    private void signIn() {

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.base_container, new SignInForm());
        fragmentTransaction.commit();
    }

    @Subscribe
    public void results(String token) {
        if (!TextUtils.isEmpty(token)) {
            Log.d(LoginForm.class.getName(), "create Account");
            setToken(token);
            ((LoginActivity) getActivity()).onTokenReceived(new MaesensAccount(username), password, token);
        } else {
            Log.d(LoginForm.class.getName(), "Token is null");
        }
    }


    private void logInFacebook() {
        mLogInButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LogIn", loginResult.getAccessToken().getUserId());
                Log.d("LogIn", loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


//public class LoginForm extends BaseNetworkFragment<EventLogIn> implements View.OnClickListener
//{
//
//    private EditText mUserName;
//    private EditText mPassword;
//    private Button mLogInButton;
//    private LoginButton mLogInButtonFacebook;
//    private CallbackManager callbackManager;
//    private LogIn mLogIn;
//
//    private final String username = "victorchuholskiy@gmail.com";
//    private final String password = "11111";
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        FacebookSdk.sdkInitialize(getActivity());
//        callbackManager = CallbackManager.Factory.create();
//        Log.d("LogIn", " LoginForm onCreate");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_login_form, container, false);
//        mUserName = (EditText) view.findViewById(R.id.login);
//        mPassword = (EditText) view.findViewById(R.id.password);
//        mLogInButton = (Button) view.findViewById(R.id.btn_sign_in);
//        mLogInButtonFacebook = (LoginButton) view.findViewById(R.id.login_button_facebook);
//        mLogInButtonFacebook.setReadPermissions("user_friends   ");
//        mLogInButtonFacebook.setFragment(this);
//
//        return view;
//    }
//
//    @Override
//    protected void loadDataFromBundle(Bundle savedInstanceState) {
//
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        AppEventsLogger.activateApp(getActivity());
//        mLogInButton.setOnClickListener(this);
//    }
//
//    @Override
//    public void onPause() {
//        AppEventsLogger.deactivateApp(getActivity());
//        mLogInButton.setOnClickListener(null);
//        super.onPause();
//    }
//
//    @Override
//    public void onFailResponse(IBaseResponse response) {
//
//    }
//
//    @Override
//    public void onSuccessResponse(IBaseResponse response) {
//        mLogIn = ((ResponseLogIn) response).getResult();
//        String token=mLogIn.getToken();
//        if (!TextUtils.isEmpty(token)) {
//            Log.d(LoginForm.class.getName(), "create Account");
//
////            ((LoginActivity) getActivity()).onTokenReceived(new MaesensAccount(username), password, token);
////
////            ((LoginActivity) getActivity()).onTokenReceived();
//            new LoginActivity().onTokenReceived(new MaesensAccount(username), password, token);
//        }
//        else {
//            Log.d(LoginForm.class.getName(), "Token is null");
//        }
//
//    }
//
//    @Override
//    public void disableControls() {
//
//    }
//
//    @Override
//    public void enableControls() {
//
//    }
//
//    @Override
//    public void sendApiRequest(String[] paramsForAPI, EventLogIn event) {
//        try {
//            ServiceHelper.getInstance().getData(paramsForAPI, event);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected String[] getApiParams() {
//        return new String[0];
//    }
//
//    @Override
//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void onApiResponse(EventLogIn event) {
//        super.onApiResponse(event);
//    }
//
//    @Override
//    public EventLogIn getEvent() {
//        return new EventLogIn();
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId())
//        {
//            case R.id.btn_sign_in:
//            {
//                logIn();
//            }
//            break;
//            case R.id.login_button_facebook:
//            {
//                logInFacebook();
//            }
//            break;
//        }
//    }
//
//    private void logIn() {
////        if (TextUtils.isEmpty(mUserName.getText()))
////        {
////                mUserName.setError(getString(R.string.login));
////            } else if (TextUtils.isEmpty(mPassword.getText())) {
////                mPassword.setError(getString(R.string.hint_password));
////            } else {
//
////            final String username = "victorchuholskiy@gmail.com";
////            final String password = "11111";
////            String accountType = "by.maesens.android";
//            sendApiRequest(new String[]{username, password}, getEvent());
//
////            IApiService api = ServiceHelper.getInstance().getRetrofit().create(IApiService.class);
////            Call<ResponseLogIn> call = api.basicLogin(username, password);
////            call.enqueue(new Callback<ResponseLogIn>() {
////                @Override
////                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
////                    if (response.isSuccessful()) {
////                        Log.d(LoginForm.class.getName(), "UserID is " + response.body().getUserId());
////                        Log.d(LoginForm.class.getName(), "Token is " + response.body().getToken());
////                        setmToken(response.body().getToken());
////                    }
//////                        Log.d(LoginForm.class.getName(),"response is bad");
////                }
////
////
////                @Override
////                public void onFailure(Call<ResponseLogIn> call, Throwable t) {
////                    Log.d(LoginForm.class.getName(), t.getMessage());
////                    Log.d(LoginForm.class.getName(), "response is failure");
////                    Log.d(LoginForm.class.getName(), String.valueOf(call.request()));
////                }
////            });
//
////            AccountManager manager=AccountManager.get(getActivity());
////            Account account = new Account(username, accountType);
////            manager.addAccountExplicitly(account, password, null);
//
//
////
//////                getLoaderManager().restartLoader(R.id.auth_token_loader, null, this);
//////            }
////        }
//    }
//
//    private void logInFacebook()
//    {
//        mLogInButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d("LogIn", loginResult.getAccessToken().getUserId());
//                Log.d("LogIn", loginResult.getAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//    }
//
//
//}

}