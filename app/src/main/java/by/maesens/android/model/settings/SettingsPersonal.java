package by.maesens.android.model.settings;

import by.maesens.android.network.responses.ResponseSettingsPersonal;

/**
 * Created by Никита on 02.04.2016.
 */
public class SettingsPersonal {
    private boolean hide_age;
    private boolean show_past;

    public SettingsPersonal(){}

    public SettingsPersonal (ResponseSettingsPersonal settingsPersonal){
        this.hide_age = settingsPersonal.isHide_age();
        this.show_past = settingsPersonal.isShow_past();
    }

    public boolean isHide_age() {
        return hide_age;
    }

    public void setHide_age(boolean hide_age) {
        this.hide_age = hide_age;
    }

    public boolean isShow_past() {
        return show_past;
    }

    public void setShow_past(boolean show_past) {
        this.show_past = show_past;
    }
}
