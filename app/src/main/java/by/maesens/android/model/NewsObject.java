package by.maesens.android.model;

/**
 * Created by Никита on 18.03.2016.
 */
public class NewsObject {
    private int id;
    private int likes_amount;
    private int max_bid;
    private String small_image;
    private String title;
    private User user;
    private Winner[] winners;
    private int amount;
    private Auction auction;
    private User author;
    private boolean is_deleted;
    private int[] likes;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes_amount() {
        return likes_amount;
    }

    public void setLikes_amount(int likes_amount) {
        this.likes_amount = likes_amount;
    }

    public int getMax_bid() {
        return max_bid;
    }

    public void setMax_bid(int max_bid) {
        this.max_bid = max_bid;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Winner[] getWinners() {
        return winners;
    }

    public void setWinners(Winner[] winners) {
        this.winners = winners;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean is_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int[] getLikes() {
        return likes;
    }

    public void setLikes(int[] likes) {
        this.likes = likes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
