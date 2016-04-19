package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Charity;

/**
 * Created by Миша on 24.02.2016.
 */
public class ResponseCharity implements IBaseResponse<List<Charity>> {
    private int count;
    private String next;
    private String previous;

    private List<Charity> results;

    @Override
    public List<Charity> getResult() {
        return results;
    }
}
