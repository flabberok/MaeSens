package by.maesens.android.network.responses;

import java.util.List;

/**
 * Created by Никита on 02.04.2016.
 */
public class ResponseSettingsNotification implements IBaseResponse<List<String>> {

    private List<String> settings;

    public ResponseSettingsNotification(List<String> list) {
        this.settings = list;
    }

    public void setList(List<String> list) {
        settings = list;
    }

    @Override
    public List<String> getResult() {
        return settings;
    }
}
