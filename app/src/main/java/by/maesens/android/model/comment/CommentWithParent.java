
package by.maesens.android.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommentWithParent {

    private int id;
    private int parentId;
    private Author author;
    private String text;
    private String dateCreate;
    private List<Like> likes = new ArrayList<Like>();

    public CommentWithParent(Comment comment, int parendId){
        this.id = comment.getId();
        this.parentId = parendId;
        this.author = comment.getAuthor();
        this.text = comment.getText();
        this.dateCreate = comment.getDateCreate();
        this.likes = comment.getLikes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
}
