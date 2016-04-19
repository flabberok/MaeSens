package by.maesens.android.network.responses;

import by.maesens.android.model.Donation;

/**
 * Created by Никита on 16.03.2016.
 */
public class ResponseDonation implements IBaseResponse<Donation> {

    private int donation;
    private int auctions_count;
    private int bids_count;
    private int auctions_win_count;

    public int getAuctions_win_count() {
        return auctions_win_count;
    }

    public void setAuctions_win_count(int auctions_win_count) {
        this.auctions_win_count = auctions_win_count;
    }

    public int getDonation() {
        return donation;
    }

    public void setDonation(int donation) {
        this.donation = donation;
    }

    public int getAuctions_count() {
        return auctions_count;
    }

    public void setAuctions_count(int auctions_count) {
        this.auctions_count = auctions_count;
    }

    public int getBids_count() {
        return bids_count;
    }

    public void setBids_count(int bids_count) {
        this.bids_count = bids_count;
    }

    @Override
    public Donation getResult() {
        return new Donation(this);
    }
}
