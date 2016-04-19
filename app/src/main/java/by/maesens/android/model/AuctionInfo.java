package by.maesens.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import by.maesens.android.utils.DateConverter;
import by.maesens.android.network.responses.ResponseAuctionInfo;

/**
 * Created by Виктор on 24.02.2016.
 *
 * "AuctionInfo" model provides information for choosed auction and have some specific information
 * about comments, bids and etc.
 */
public class AuctionInfo implements Parcelable{
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

    public AuctionInfo(ResponseAuctionInfo resp) {
        this.id = resp.getId();
        this.user = resp.getUser();
        this.is_online = resp.getIs_online();
        this.description = resp.getDescription();
        this.title = resp.getTitle();
        this.bids = resp.getBids();
        this.image = resp.getImage();
        this.unique_image = resp.getUnique_image();
        this.small_image = resp.getSmall_image();
        this.img_height = resp.getImg_height();
        this.img_width = resp.getImg_width();
        this.is_favourite = resp.getIs_favourite();
        this.is_ended = resp.getIs_ended();
        this.likes = resp.getLikes();
        this.charity = resp.getCharity();
        this.category = resp.getCategory();
        this.can_edit = resp.getCan_edit();
        this.start = resp.getStart();
        this.end = resp.getEnd();
        this.likes_amount = resp.getLikes_amount();
        this.gender = resp.getGender();
        this.min_bid = resp.getMin_bid();
        this.last_bid = resp.getLast_bid();
        this.winners = resp.getWinners();
        this.max_winners = resp.getMax_winners();
        this.location = resp.getLocation();
        this.auction_views = resp.getAuction_views();
        this.is_liked = resp.getIs_liked();
    }

    private AuctionInfo(Parcel parcel) {
        id = parcel.readInt();
        user = parcel.readParcelable(User.class.getClassLoader());
        is_online = (parcel.readByte() != 0);
        description = parcel.readString();
        title = parcel.readString();

        Parcelable[] parcelableArray = parcel.readParcelableArray(Bid.class.getClassLoader());
        if (parcelableArray != null) {
            bids = Arrays.copyOf(parcelableArray, parcelableArray.length, Bid[].class);
        }

        image = parcel.readString();
        unique_image = parcel.readString();
        small_image = parcel.readString();
        img_height = parcel.readInt();
        img_width = parcel.readInt();
        is_favourite = (parcel.readByte() != 0);
        is_ended = (parcel.readByte() != 0);
        parcel.readIntArray(likes);
        charity = parcel.readParcelable(Charity.class.getClassLoader());
        category = parcel.readParcelable(Category.class.getClassLoader());
        can_edit = (parcel.readByte() != 0);
        start = parcel.readString();
        end = parcel.readString();
        likes_amount = parcel.readInt();
        gender = parcel.readInt();
        min_bid = parcel.readInt();
        last_bid = parcel.readInt();

        parcelableArray = parcel.readParcelableArray(Winner.class.getClassLoader());
        if (parcelableArray != null) {
            winners = Arrays.copyOf(parcelableArray, parcelableArray.length, Winner[].class);
        }

        max_winners = parcel.readInt();
        location = parcel.readParcelable(Location.class.getClassLoader());
        auction_views = parcel.readInt();
        is_liked = (parcel.readByte() != 0);
    }

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

    public Date getEndDate(){
        return DateConverter.convertStringToDate(end, "yyyy-MM-dd");
    }

    public List<Bid> getAuctionBidsStory(){
        return new ArrayList<Bid>(Arrays.asList(bids));
    }

    public List<Bid> getAuctionLeaders(){
        // Сейчас наша задача получить массив ставок с максимальными значениями и уникальными пользователями.
        // Например бывает так, что один пользователь делает несколько ставок подряд, и все они в топе (без понятия зачем).
        // Соответственно нужно сгруппировать массив по пользователям, а потом выбрать максимум.
        if (bids.length > 0) {
            HashMap<Integer, Bid> hashMap = new HashMap<>();
            for (int i = bids.length - 1; i >= 0; i--) { // Воспользуемся расположением элементов в исходном массиве
                hashMap.put(bids[i].getUser().getId(), bids[i]);
            }
            List<Bid> maxAmountBids = new ArrayList<>(hashMap.values()); // Получаем список максимальных ставок с уникальными пользователями
            Collections.sort(maxAmountBids, new BidsComparatorByAmount());
            return maxAmountBids.subList(0, Math.min(max_winners, maxAmountBids.size()));
        }
        else
            return null;
    }

    private class BidsComparatorByAmount implements Comparator<Bid> {
        @Override
        public int compare(Bid lhs, Bid rhs) {
            return lhs.getAmount() < rhs.getAmount() ? 1 : lhs.getAmount() == rhs.getAmount() ? 0 : -1;
        }
    }

    private class BidsComparatorByDate implements Comparator<Bid> {
        @Override
        public int compare(Bid lhs, Bid rhs) {
            if ((lhs.getDate() != null) && (rhs.getDate() != null))
                return lhs.getDate().compareTo(rhs.getDate());
            else
                return ((Integer)lhs.getId()).compareTo(lhs.getId()); // если даты оказались null, то сортируем по id
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(user, flags);
        dest.writeByte((byte) (is_online ? 1 : 0));
        dest.writeString(description);
        dest.writeString(title);
        dest.writeParcelableArray(bids, flags);
        dest.writeString(image);
        dest.writeString(unique_image);
        dest.writeString(small_image);
        dest.writeInt(img_height);
        dest.writeInt(img_width);
        dest.writeByte((byte) (is_favourite ? 1 : 0));
        dest.writeByte((byte) (is_ended ? 1 : 0));
        dest.writeIntArray(likes);
        dest.writeParcelable(charity, flags);
        dest.writeParcelable(category, flags);
        dest.writeByte((byte) (can_edit ? 1 : 0));
        dest.writeString(start);
        dest.writeString(end);
        dest.writeInt(likes_amount);
        dest.writeInt(gender);
        dest.writeInt(min_bid);
        dest.writeInt(last_bid);
        dest.writeParcelableArray(winners, flags);
        dest.writeInt(max_winners);
        dest.writeParcelable(location, flags);
        dest.writeInt(auction_views);
        dest.writeByte((byte) (is_liked ? 1 : 0));
    }

    public static final Parcelable.Creator<AuctionInfo> CREATOR = new Parcelable.Creator<AuctionInfo>() {   // Статический метод с помощью которого создаем обьект
        public AuctionInfo createFromParcel(Parcel in) {
            return new AuctionInfo(in);
        }

        public AuctionInfo[] newArray(int size) {
            return new AuctionInfo[size];
        }
    };
}