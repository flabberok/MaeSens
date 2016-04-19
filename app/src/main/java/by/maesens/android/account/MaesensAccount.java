package by.maesens.android.account;

import android.accounts.Account;
import android.os.Parcel;
import android.util.Log;

/**
 * Created by Павел on 11.04.2016.
 */
public class MaesensAccount extends Account {

    public static final String TYPE = "by.maesens.android";

    public static final String TOKEN_FULL_ACCESS = "by.maesens.android.TOKEN_FULL_ACCESS";

    public static final String KEY_PASSWORD = "by.maesens.android.KEY_PASSWORD";


    public MaesensAccount(String name) {
        super(name, TYPE);
        Log.d("Login", "Maesens account");
    }

    public MaesensAccount(Parcel in) {
        super(in);
    }
}
