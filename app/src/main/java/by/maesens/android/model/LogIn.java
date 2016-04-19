package by.maesens.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.maesens.android.network.responses.ResponseLogIn;

/**
 * Created by Павел on 29.03.2016.
 */
public class LogIn {



    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("vk_auth_url")
    @Expose
    private String vkAuthUrl;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("sentry")
    @Expose
    private boolean sentry;
    @SerializedName("PUSHER_API_KEY")
    @Expose
    private String PUSHERAPIKEY;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("moderator")
    @Expose
    private boolean moderator;
    @SerializedName("has_project")
    @Expose
    private boolean hasProject;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("phone_verified")
    @Expose
    private boolean phoneVerified;
    @SerializedName("anonymous")
    @Expose
    private boolean anonymous;
    @SerializedName("fb_auth_url")
    @Expose
    private String fbAuthUrl;
    @SerializedName("serverApi")
    @Expose
    private String serverApi;
    @SerializedName("location_id")
    @Expose
    private int locationId;
    @SerializedName("is_comment_blocked")
    @Expose
    private boolean isCommentBlocked;
    @SerializedName("location")
    @Expose
    private String location;


    public LogIn(ResponseLogIn responseLogIn) {
        this.firstName = responseLogIn.getFirstName();
        this.location = responseLogIn.getLocation();
        this.isCommentBlocked = responseLogIn.isCommentBlocked();
        this.locationId = responseLogIn.getLocationId();
        this.serverApi = responseLogIn.getServerApi();
        this.fbAuthUrl = responseLogIn.getFbAuthUrl();
        this.anonymous = responseLogIn.isAnonymous();
        this.phoneVerified = responseLogIn.isPhoneVerified();
        this.token = responseLogIn.getToken();
        this.hasProject = responseLogIn.isHasProject();
        this.moderator = responseLogIn.isModerator();
        this.avatar = responseLogIn.getAvatar();
        this.PUSHERAPIKEY = responseLogIn.getPUSHERAPIKEY();
        this.sentry = responseLogIn.isSentry();
        this.userId = responseLogIn.getUserId();
        this.vkAuthUrl = responseLogIn.getVkAuthUrl();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getVkAuthUrl() {
        return vkAuthUrl;
    }

    public void setVkAuthUrl(String vkAuthUrl) {
        this.vkAuthUrl = vkAuthUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isSentry() {
        return sentry;
    }

    public void setSentry(boolean sentry) {
        this.sentry = sentry;
    }

    public String getPUSHERAPIKEY() {
        return PUSHERAPIKEY;
    }

    public void setPUSHERAPIKEY(String PUSHERAPIKEY) {
        this.PUSHERAPIKEY = PUSHERAPIKEY;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }

    public boolean isHasProject() {
        return hasProject;
    }

    public void setHasProject(boolean hasProject) {
        this.hasProject = hasProject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getFbAuthUrl() {
        return fbAuthUrl;
    }

    public void setFbAuthUrl(String fbAuthUrl) {
        this.fbAuthUrl = fbAuthUrl;
    }

    public String getServerApi() {
        return serverApi;
    }

    public void setServerApi(String serverApi) {
        this.serverApi = serverApi;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isCommentBlocked() {
        return isCommentBlocked;
    }

    public void setIsCommentBlocked(boolean isCommentBlocked) {
        this.isCommentBlocked = isCommentBlocked;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
