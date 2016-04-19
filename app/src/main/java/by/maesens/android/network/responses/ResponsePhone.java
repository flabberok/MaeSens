package by.maesens.android.network.responses;

/**
 * Created by Никита on 03.04.2016.
 */
public class ResponsePhone implements IBaseResponse<String>{

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getResult() {
        return phone;
    }
}
