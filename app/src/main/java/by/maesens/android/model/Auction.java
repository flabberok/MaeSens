package by.maesens.android.model;

/**
 * Created by Никита on 13.02.2016.
 *
 * "Auction" model provides information for cards in the auction list.
 */
public class Auction {
    private int id;
    private String title;
    private String start;
    private String end;
    private Category category;
    private String small_image;
    private String unique_image;
    private int max_bid;
    private boolean is_ended;
    private User user;
    private String small_image_132x132;
    private String small_unique_132x132;
    private int[] likes;
    private Charity charity;
    private String description;
    private String location;
    private int last_bid;
    private int likes_amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getUnique_image() {
        return unique_image;
    }

    public void setUnique_image(String unique_image) {
        this.unique_image = unique_image;
    }

    public int getMax_bid() {
        return max_bid;
    }

    public void setMax_bid(int max_bid) {
        this.max_bid = max_bid;
    }

    public boolean is_ended() {
        return is_ended;
    }

    public void setIs_ended(boolean is_ended) {
        this.is_ended = is_ended;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSmall_image_132x132() {
        return small_image_132x132;
    }

    public void setSmall_image_132x132(String small_image_132x132) {
        this.small_image_132x132 = small_image_132x132;
    }

    public String getSmall_unique_132x132() {
        return small_unique_132x132;
    }

    public void setSmall_unique_132x132(String small_unique_132x132) {
        this.small_unique_132x132 = small_unique_132x132;
    }

    public int[] getLikes() {
        return likes;
    }

    public void setLikes(int[] likes) {
        this.likes = likes;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLast_bid() {
        return last_bid;
    }

    public void setLast_bid(int last_bid) {
        this.last_bid = last_bid;
    }

    public int getLikes_amount() {
        return likes_amount;
    }

    public void setLikes_amount(int likes_amount) {
        this.likes_amount = likes_amount;
    }
}
