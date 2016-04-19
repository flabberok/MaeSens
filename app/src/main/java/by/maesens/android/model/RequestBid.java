package by.maesens.android.model;

/**
 * Created by Виктор on 13.04.2016.
 */
public class RequestBid {
    private int amount;
    private boolean hidden;

    public RequestBid(int amount, boolean hidden) {
        this.amount = amount;
        this.hidden = hidden;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
