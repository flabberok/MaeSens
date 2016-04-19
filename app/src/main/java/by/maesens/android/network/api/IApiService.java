package by.maesens.android.network.api;

import java.util.List;

import by.maesens.android.model.Association;
import by.maesens.android.model.Auction;
import by.maesens.android.model.Category;
import by.maesens.android.model.Location;
import by.maesens.android.model.RequestBid;
import by.maesens.android.model.Tag;
import by.maesens.android.model.User;
import by.maesens.android.model.settings.SettingsCode;
import by.maesens.android.model.settings.SettingsEmail;
import by.maesens.android.model.settings.SettingsNotification;
import by.maesens.android.model.settings.SettingsPersonal;
import by.maesens.android.model.settings.SettingsPhone;
import by.maesens.android.network.responses.ResponseBid;
import by.maesens.android.network.responses.ResponseComments;
import by.maesens.android.network.responses.ResponseAuctionInfo;
import by.maesens.android.network.responses.ResponseAuctions;
import by.maesens.android.network.responses.ResponseAuctionsCreated;
import by.maesens.android.network.responses.ResponseAuctionsFavourite;
import by.maesens.android.network.responses.ResponseAuctionsWon;
import by.maesens.android.network.responses.ResponseCharity;
import by.maesens.android.network.responses.ResponseCode;
import by.maesens.android.network.responses.ResponseDonation;
import by.maesens.android.network.responses.ResponseEmail;
import by.maesens.android.network.responses.ResponseLogIn;
import by.maesens.android.network.responses.ResponseMyBids;
import by.maesens.android.network.responses.ResponseNews;
import by.maesens.android.network.responses.ResponseOrder;
import by.maesens.android.network.responses.ResponsePhone;
import by.maesens.android.network.responses.ResponseProfile;
import by.maesens.android.network.responses.ResponseProject;
import by.maesens.android.network.responses.ResponseSettingsContacts;
import by.maesens.android.network.responses.ResponseSettingsNotification;
import by.maesens.android.network.responses.ResponseSettingsPersonal;
import by.maesens.android.network.responses.ResponseTag;
import by.maesens.android.network.responses.ResponseUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiService {
    @GET("api/auctions/popular/")
    Call<List<Auction>> getAuctionsPopular();

    @GET("api/auctions/favourite/")
    Call<ResponseAuctionsFavourite> getAuctionsFavourite(@Header("Authorization") String authorization);

    @GET("api/auctions/my-orders/")
    Call<ResponseAuctionsWon> getAuctionsWon(@Header("Authorization") String authorization);

    @GET("api/auctions/{id}/list/")
    Call<ResponseAuctionsCreated> getAuctionsCreated(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("api/auctions/")
    Call<ResponseAuctions> getAuctionsSearch(@Query("page") String pageNumber,
                                             @Query("order") String order,
                                             @Query("show_all") String showAll,
                                             @Query("auction_type") String showGroup,
                                             @Query("location") String city,
                                             @Query("category") String category);

    @GET("api/auctions/")
    Call<ResponseAuctions> getAuctionsUnique(@Query("unique") String query);

    @GET("api/auctions/{id}/")
    Call<ResponseAuctionInfo> getAuction(@Path("id") String id);


    @GET("api/auctions/new/")
    Call<List<Auction>> getAuctionsNew();

    @GET("api/locations/")
    Call<List<Location>> getLocations();

    @GET("api/category/")
    Call<List<Category>> getAuctionCategory();

    @GET("/api/charity/category/")
    Call<List<Category>> getCharityCategory();

    @GET("/api/charity/")
    Call<ResponseCharity> getCharity(@Query("page") String pageNumber,
                                     @Query("category") String category,
                                     @Query("order") String sort
    );

    @GET("/api/auctions/{id}/comments/")
    Call<ResponseComments> getAuctionComments(@Path("id") String id);

    @GET("/api/charity/{id}/comments/")
    Call<ResponseComments> getCharityComments(@Path("id") String id);

    @GET("/api/charity/{slug}/")
    Call<ResponseProject> getProject(@Path("slug") String slug, @Header("Authorization") String authorization);

    @GET("api/auctions/{slug}/charity/")
    Call<ResponseAuctions> getProjectChildrenAuction(@Path("slug") String slug);


    @GET("api/charity/{slug}/orders/")
    Call<ResponseOrder> getProjectStatistics(@Path("slug") String slug, @Query("page") String page,
                                             @Header("Authorization") String authorization);


    @GET("/api/profile/")
    Call<ResponseProfile> getProfile(@Header("Authorization") String authorization);

    @GET("/api/donation/{id}/")
    Call<ResponseDonation> getDonation(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("/api/auctions/{id}/profile-list/")
    Call<List<Auction>> getProfileList(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("/api/profile/orders/")
    Call<ResponseOrder> getOrders(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/mobile/login/")
    Call<ResponseLogIn> basicLogin(@Field(value = "email", encoded = true) String email,
                                   @Field(value = "password", encoded = true) String password);

    @GET("/api/news/")
    Call<ResponseNews> getNews(@Header("Authorization") String authorization, @Query("page") String pageNumber);

    @GET("/api/users/{id}/")
    Call<ResponseUser> getUser(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("/api/profile/association/{id}/")
    Call<List<Association>> getAssociation(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("/api/auctions/my-bids/")
    Call<ResponseMyBids> getMyBids(@Header("Authorization") String authorization);

    @PUT("/api/profile/update/")
    Call<ResponseUser> updateUser(@Header("Authorization") String authorization, @Body User userUpdate);

    @GET("/api/profile/update/")
    Call<ResponseUser> getUserSettings(@Header("Authorization") String authorization);

    @GET("/api/profile/personal/")
    Call<ResponseSettingsPersonal> getSettingsPersonal(@Header("Authorization") String authorization);

    @PUT("/api/profile/personal/")
    Call<ResponseSettingsPersonal> updateSettingsPersonal(@Header("Authorization") String authorization, @Body SettingsPersonal settingsPersonal);

    @GET("/api/users/get_tags/")
    Call<List<Tag>> getTags(@Header("Authorization") String authorization);

    @POST("/api/users/add_tag/")
    Call<ResponseTag> addTag(@Header("Authorization") String authorization, @Body Tag tag);

    @DELETE("/api/users/remove_tag/{id}/")
    Call<ResponseTag> deleteTag(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("/api/profile/update_settings/")
    Call<ResponseSettingsNotification> getNotificationSettings(@Header("Authorization") String authorization);

    @PUT("/api/profile/update_settings/")
    Call<ResponseSettingsNotification> updateNotificationSettings(@Header("Authorization") String authorization, @Body SettingsNotification settings);

    @GET("/api/profile/contacts/")
    Call<ResponseSettingsContacts> getSettingsContacts(@Header("Authorization") String authorization);

    @POST("/api/profile/email_change/")
    Call<ResponseEmail> updateEmail(@Header("Authorization") String authorization, @Body SettingsEmail email);

    @POST("/api/verify_phone/")
    Call<ResponsePhone> verifyPhone(@Header("Authorization") String authorization, @Body SettingsPhone phone);

    @POST("/api/check_code/")
    Call<ResponseCode> checkCode(@Header("Authorization") String authorization, @Body SettingsCode code);

    @POST("/api/auctions/{id}/create-bid/")
    Call<ResponseBid> doBid(@Header("Authorization") String authorization,
                                @Path("id") String id,
                                @Body RequestBid requestBid);
}
