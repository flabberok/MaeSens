package by.maesens.android.network.responses;

import by.maesens.android.model.Location;
import by.maesens.android.model.Profile;

/**
 * Created by Никита on 16.03.2016.
 */
public class ResponseProfile implements IBaseResponse<Profile> {

    private String first_name;
    private String last_name;
    private int gender;
    private String about;
    private String birth_date;
    private String profile_avatar;
    private String date_joined;
    private Location location;
    private String phone;
    private String email;
    private boolean email_verified;
    private boolean phone_verified;
    private int[] phone_verify;
    private int friends_count;
    private boolean unpaid_auctions;
    private int balance;
    private boolean is_online;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(String profile_avatar) {
        this.profile_avatar = profile_avatar;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public boolean isPhone_verified() {
        return phone_verified;
    }

    public void setPhone_verified(boolean phone_verified) {
        this.phone_verified = phone_verified;
    }

    public int[] getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(int[] phone_verify) {
        this.phone_verify = phone_verify;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public boolean isUnpaid_auctions() {
        return unpaid_auctions;
    }

    public void setUnpaid_auctions(boolean unpaid_auctions) {
        this.unpaid_auctions = unpaid_auctions;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean is_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    @Override
    public Profile getResult() {
        return new Profile(this);
    }
}
