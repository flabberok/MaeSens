package by.maesens.android.network.responses;


import java.util.List;

import by.maesens.android.model.Auction;

/**
 * Created by USER on 18.02.2016.
 */
public class ResponsePopularAuctions implements IBaseResponse<List<Auction>> {

    private List<Auction> results;

    public ResponsePopularAuctions(List<Auction> results) {
        this.results = results;
    }

    @Override
    public List<Auction> getResult() {
        return results;
    }

    public void setResults(List<Auction> results) {
        this.results = results;
    }
}
