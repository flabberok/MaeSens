package by.maesens.android.network.responses;

/**
 * Created by Никита on 03.04.2016.
 */
public class ResponseCode implements IBaseResponse<String> {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getResult() {
        return code;
    }
}
