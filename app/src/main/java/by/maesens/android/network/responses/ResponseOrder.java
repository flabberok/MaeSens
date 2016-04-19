package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Order;

/**
 * Created by Никита on 16.03.2016.
 */
public class ResponseOrder implements IBaseResponse<List<Order>> {

    private int count;
    private String next;
    private String previous;
    private List<Order> results;

    @Override
    public List<Order> getResult() {
        return results;
    }
}
