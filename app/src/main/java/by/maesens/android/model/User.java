package by.maesens.android.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import by.maesens.android.network.responses.ResponseUser;

/**
 * Created by Виктор on 14.03.2016.
 */
public class User implements Parcelable {
    private int id;
    private String first_name;
    private String birth_date;
    private String small_avatar;
    private boolean is_online;
    private String profile_avatar;
    private String small_uuid;
    private String about;
    private boolean can_invite;
    private boolean can_send_message;
    private String date_joined;
    private boolean email_verified;
    private int friends_count;
    private User[] friends_limited;
    private int gender;
    private boolean is_blocked;
    private boolean is_comment_blocked;
    private boolean is_subscribed;
    private Location location;
    private boolean phone_verified;
    private String[] tags;
    private boolean unpaid_auctions;
    private String update_avatar;
    private String avatar;
    private String last_name;

    public User(){

    }

    public User(ResponseUser responseUser) {
        this.id = responseUser.getId();
        this.first_name = responseUser.getFirst_name();
        this.birth_date = responseUser.getBirth_date();
        this.small_avatar = responseUser.getSmall_avatar();
        this.is_online = responseUser.is_online();
        this.profile_avatar = responseUser.getProfile_avatar();
        this.small_uuid = responseUser.getSmall_uuid();
        this.about = responseUser.getAbout();
        this.can_invite = responseUser.isCan_invite();
        this.can_send_message = responseUser.isCan_send_message();
        this.date_joined = responseUser.getDate_joined();
        this.email_verified = responseUser.isEmail_verified();
        this.friends_count = responseUser.getFriends_count();
        this.friends_limited = responseUser.getFriends_limited();
        this.gender = responseUser.getGender();
        this.is_blocked = responseUser.is_blocked();
        this.is_comment_blocked = responseUser.is_comment_blocked();
        this.is_subscribed = responseUser.is_subscribed();
        this.location = responseUser.getLocation();
        this.phone_verified = responseUser.isPhone_verified();
        this.tags = responseUser.getTags();
        this.unpaid_auctions = responseUser.isUnpaid_auctions();
        this.update_avatar = responseUser.getUpdate_avatar();
        this.avatar = responseUser.getAvatar();
        this.last_name = responseUser.getLast_name();
    }

    protected User(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        birth_date = in.readString();
        small_avatar = in.readString();
        is_online = in.readByte() != 0;
        profile_avatar = in.readString();
        small_uuid = in.readString();
        about = in.readString();
        can_invite = in.readByte() != 0;
        can_send_message = in.readByte() != 0;
        date_joined = in.readString();
        email_verified = in.readByte() != 0;
        friends_count = in.readInt();
        friends_limited = in.createTypedArray(User.CREATOR);
        gender = in.readInt();
        is_blocked = in.readByte() != 0;
        is_comment_blocked = in.readByte() != 0;
        is_subscribed = in.readByte() != 0;
        location = in.readParcelable(Location.class.getClassLoader());
        phone_verified = in.readByte() != 0;
        tags = in.createStringArray();
        unpaid_auctions = in.readByte() != 0;
        update_avatar = in.readString();
        avatar = in.readString();
        last_name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUpdate_avatar() {
        return update_avatar;
    }

    public void setUpdate_avatar(String update_avatar) {
        this.update_avatar = update_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSmall_avatar() {
        return small_avatar;
    }

    public void setSmall_avatar(String small_avatar) {
        this.small_avatar = small_avatar;
    }

    public boolean getIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(String profile_avatar) {
        this.profile_avatar = profile_avatar;
    }

    public String getSmall_uuid() {
        return small_uuid;
    }

    public void setSmall_uuid(String small_uuid) {
        this.small_uuid = small_uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        return id == user.id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isCan_invite() {
        return can_invite;
    }

    public void setCan_invite(boolean can_invite) {
        this.can_invite = can_invite;
    }

    public boolean isCan_send_message() {
        return can_send_message;
    }

    public void setCan_send_message(boolean can_send_message) {
        this.can_send_message = can_send_message;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public User[] getFriends_limited() {
        return friends_limited;
    }

    public void setFriends_limited(User[] friends_limited) {
        this.friends_limited = friends_limited;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean is_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean is_comment_blocked() {
        return is_comment_blocked;
    }

    public void setIs_comment_blocked(boolean is_comment_blocked) {
        this.is_comment_blocked = is_comment_blocked;
    }

    public boolean is_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(boolean is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPhone_verified() {
        return phone_verified;
    }

    public void setPhone_verified(boolean phone_verified) {
        this.phone_verified = phone_verified;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isUnpaid_auctions() {
        return unpaid_auctions;
    }

    public void setUnpaid_auctions(boolean unpaid_auctions) {
        this.unpaid_auctions = unpaid_auctions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(first_name);
        dest.writeString(birth_date);
        dest.writeString(small_avatar);
        dest.writeByte((byte) (is_online ? 1 : 0));
        dest.writeString(profile_avatar);
        dest.writeString(small_uuid);
        dest.writeString(about);
        dest.writeByte((byte) (can_invite ? 1 : 0));
        dest.writeByte((byte) (can_send_message ? 1 : 0));
        dest.writeString(date_joined);
        dest.writeByte((byte) (email_verified ? 1 : 0));
        dest.writeInt(friends_count);
        dest.writeTypedArray(friends_limited, flags);
        dest.writeInt(gender);
        dest.writeByte((byte) (is_blocked ? 1 : 0));
        dest.writeByte((byte) (is_comment_blocked ? 1 : 0));
        dest.writeByte((byte) (is_subscribed ? 1 : 0));
        dest.writeParcelable(location, flags);
        dest.writeByte((byte) (phone_verified ? 1 : 0));
        dest.writeStringArray(tags);
        dest.writeByte((byte) (unpaid_auctions ? 1 : 0));
        dest.writeString(update_avatar);
        dest.writeString(avatar);
        dest.writeString(last_name);
    }
}
