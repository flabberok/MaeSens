package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Auction;

/**
 * Created by Никита on 18.04.2016.
 */
public class ResponseAuctionsCreated implements IBaseResponse<List<Auction>> {
    private int count;
    private String next;
    private String previous;
    protected List<Auction> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Auction> getResults() {
        return results;
    }

    @Override
    public List<Auction> getResult() {
        return results;
    }

    public void setResult(List<Auction> results) {
        this.results = results;
    }
}
