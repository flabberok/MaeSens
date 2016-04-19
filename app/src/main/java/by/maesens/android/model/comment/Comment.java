
package by.maesens.android.model.comment;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("likes")
    @Expose
    private List<Like> likes = new ArrayList<Like>();
    @SerializedName("child")
    @Expose
    private Child child;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

}
