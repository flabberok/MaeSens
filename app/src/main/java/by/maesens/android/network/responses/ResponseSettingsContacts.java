package by.maesens.android.network.responses;

import by.maesens.android.model.settings.SettingsContacts;

/**
 * Created by Никита on 02.04.2016.
 */
public class ResponseSettingsContacts implements IBaseResponse<SettingsContacts> {

    private String email;
    private boolean email_verified;
    private String phone;

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

    @Override
    public SettingsContacts getResult() {
        return new SettingsContacts(this);
    }
}
