package by.maesens.android.model.settings;

import by.maesens.android.network.responses.ResponseSettingsContacts;

/**
 * Created by Никита on 02.04.2016.
 */
public class SettingsContacts {
    private String email;
    private boolean email_verified;
    private String phone;

    public SettingsContacts(ResponseSettingsContacts responseSettingsContacts){
        this.email = responseSettingsContacts.getEmail();
        this.email_verified = responseSettingsContacts.isEmail_verified();
        this.phone = responseSettingsContacts.getPhone();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
