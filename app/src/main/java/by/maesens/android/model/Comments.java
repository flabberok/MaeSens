package by.maesens.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import by.maesens.android.model.comment.Comment;
import by.maesens.android.network.responses.ResponseComments;

/**
 * Created by Павел on 17.03.2016.
 */
public class Comments  {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<Comment> results = new ArrayList<Comment>();


    public Comments (ResponseComments response)
    {
        this.count=response.getCount();
        this.next=response.getNext();
        this.previous=response.getPrevious();
        this.results=response.getResult();
    }

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

    public List<Comment> getResults() {
        return results;
    }

    public void setResults(List<Comment> results) {
        this.results = results;
    }


}
