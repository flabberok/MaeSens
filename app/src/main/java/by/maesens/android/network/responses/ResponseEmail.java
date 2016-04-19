package by.maesens.android.network.responses;

/**
 * Created by Никита on 03.04.2016.
 */
public class ResponseEmail implements IBaseResponse<String> {

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getResult() {
        return email;
    }
}
