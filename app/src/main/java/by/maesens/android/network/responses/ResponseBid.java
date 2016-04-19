package by.maesens.android.network.responses;

import by.maesens.android.model.Bid;
import by.maesens.android.model.User;

/**
 * Created by Виктор on 13.04.2016.
 */
public class ResponseBid implements IBaseResponse<Bid>{
    int id;
    int amount;
    User user;
    String date;
    boolean hidden;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public Bid getResult() {
        return new Bid(this);
    }
}
