package by.maesens.android.network.responses;

import by.maesens.android.model.AuctionInfo;
import by.maesens.android.model.Bid;
import by.maesens.android.model.Category;
import by.maesens.android.model.Charity;
import by.maesens.android.model.Location;
import by.maesens.android.model.User;
import by.maesens.android.model.Winner;

/**
 * Created by Виктор on 24.02.2016.
 */
public class ResponseAuctionInfo implements IBaseResponse<AuctionInfo> {
    private int id;
    private User user;
    private boolean is_online;
    private String description;
    private String title;
    private Bid[] bids;
    private String image;
    private String unique_image;
    private String small_image;
    private int img_height;
    private int img_width;
    private boolean is_favourite;
    private boolean is_ended;
    private int[] likes;
    private Charity charity;
    private Category category;
    private boolean can_edit;
    private String start;
    private String end;
    private int likes_amount;
    private int gender;
    private int min_bid;
    private int last_bid;
    private Winner[] winners;
    private int max_winners;
    private Location location;
    private int auction_views;
    private boolean is_liked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bid[] getBids() {
        return bids;
    }

    public void setBids(Bid[] bids) {
        this.bids = bids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnique_image() {
        return unique_image;
    }

    public void setUnique_image(String unique_image) {
        this.unique_image = unique_image;
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    public boolean getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }

    public boolean getIs_ended() {
        return is_ended;
    }

    public void setIs_ended(boolean is_ended) {
        this.is_ended = is_ended;
    }

    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public int[] getLikes() {
        return likes;
    }

    public void setLikes(int[] likes) {
        this.likes = likes;
    }

    public boolean getIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getCan_edit() {
        return can_edit;
    }

    public void setCan_edit(boolean can_edit) {
        this.can_edit = can_edit;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getLikes_amount() {
        return likes_amount;
    }

    public void setLikes_amount(int likes_amount) {
        this.likes_amount = likes_amount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMin_bid() {
        return min_bid;
    }

    public void setMin_bid(int min_bid) {
        this.min_bid = min_bid;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public int getLast_bid() {
        return last_bid;
    }

    public void setLast_bid(int last_bid) {
        this.last_bid = last_bid;
    }

    public Winner[] getWinners() {
        return winners;
    }

    public void setWinners(Winner[] winners) {
        this.winners = winners;
    }

    public int getMax_winners() {
        return max_winners;
    }

    public void setMax_winners(int max_winners) {
        this.max_winners = max_winners;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getAuction_views() {
        return auction_views;
    }

    public void setAuction_views(int auction_views) {
        this.auction_views = auction_views;
    }

    public boolean getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public AuctionInfo getResult() {
        return new AuctionInfo(this);
    }
}
