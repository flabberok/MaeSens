package by.maesens.android.model;

import by.maesens.android.network.responses.ResponseDonation;

/**
 * Created by Никита on 16.03.2016.
 */
public class Donation {

    private int donation;
    private int auctions_count;
    private int bids_count;
    private int auctions_win_count;

    public Donation(ResponseDonation responseDonation){
        this.donation = responseDonation.getDonation();
        this.auctions_count = responseDonation.getAuctions_count();
        this.bids_count = responseDonation.getBids_count();
        this.auctions_win_count = responseDonation.getAuctions_win_count();
    }

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
}
