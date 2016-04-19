package by.maesens.android.network.responses;

import android.util.Log;

import by.maesens.android.model.Category;
import by.maesens.android.model.Charity;
import by.maesens.android.model.User;

/**
 * Created by Sol on 19.03.2016.
 */
public class ResponseProject implements IBaseResponse<Charity> {


    Object[] rewards;
    int ordering;


    private int id;
    private String title;
    private Category[] category;
    private String purpose;
    private long required_amount;
    private long current_amount;
    private String final_date;
    private String slug;
    private boolean is_ended;
    private String small_image;
    private String image_140x140;
    private String image_64x64;
    private String description;
    private String location;
    private int last_bid;
    private int likes_amount;
    private boolean is_published;
    private String facebook_url;
    private String vk_url;
    private String twitter_url;
    private String youtube_url;
    private String slideshare_url;
    private String site_url;
    private boolean is_social;
    private boolean is_commercial;
    private User user;
    private String background_image;

    public Object[] getRewards() {
        return rewards;
    }

    public void setRewards(Object[] rewards) {
        this.rewards = rewards;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

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

    public Category[] getCategory() {
        return category;
    }

    public void setCategory(Category[] category) {
        this.category = category;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public long getRequired_amount() {
        return required_amount;
    }

    public void setRequired_amount(long required_amount) {
        this.required_amount = required_amount;
    }

    public long getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(long current_amount) {
        this.current_amount = current_amount;
    }

    public String getFinal_date() {
        return final_date;
    }

    public void setFinal_date(String final_date) {
        this.final_date = final_date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean is_ended() {
        return is_ended;
    }

    public void setIs_ended(boolean is_ended) {
        this.is_ended = is_ended;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getImage_140x140() {
        return image_140x140;
    }

    public void setImage_140x140(String image_140x140) {
        this.image_140x140 = image_140x140;
    }

    public String getImage_64x64() {
        return image_64x64;
    }

    public void setImage_64x64(String image_64x64) {
        this.image_64x64 = image_64x64;
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

    public boolean is_published() {
        return is_published;
    }

    public void setIs_published(boolean is_published) {
        this.is_published = is_published;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public String getVk_url() {
        return vk_url;
    }

    public void setVk_url(String vk_url) {
        this.vk_url = vk_url;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public String getSlideshare_url() {
        return slideshare_url;
    }

    public void setSlideshare_url(String slideshare_url) {
        this.slideshare_url = slideshare_url;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public boolean is_social() {
        return is_social;
    }

    public void setIs_social(boolean is_social) {
        this.is_social = is_social;
    }

    public boolean is_commercial() {
        return is_commercial;
    }

    public void setIs_commercial(boolean is_commercial) {
        this.is_commercial = is_commercial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    @Override
    public Charity getResult() {
        Log.d("ResponseProject", "getResult() ");
        return new Charity(this);
    }

}
