package by.maesens.android.model;

/**
 * Created by Никита on 16.03.2016.
 */
public class Order {

    private String payment_date;
    private int amount;
    private int payment_type;
    private AuctionShort auction;
    private Charity charity;
    private User user;

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public AuctionShort getAuction() {
        return auction;
    }

    public void setAuction(AuctionShort auction) {
        this.auction = auction;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
