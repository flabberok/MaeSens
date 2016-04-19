package by.maesens.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

import by.maesens.android.network.responses.ResponseBid;
import by.maesens.android.utils.DateConverter;

/**
 * Created by Виктор on 25.02.2016.
 */
public class Bid implements Parcelable{
    private int id;
    private int amount;
    private User user;
    private String date;
    private boolean hidden;

    private Bid(Parcel parcel) {
        id = parcel.readInt();
        amount = parcel.readInt();
        date = parcel.readString();
        user = parcel.readParcelable(User.class.getClassLoader());
        boolean[] booleanParams = null;
        parcel.readBooleanArray(booleanParams);
        hidden = booleanParams[0];
    }

    public Bid(ResponseBid response) {
        this.id = response.getId();
        this.amount = response.getAmount();
        this.user = response.getUser();
        this.date = response.getDate();
        this.hidden = response.getHidden();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateStr() {
        return date;
    }

    public Date getDate() {
        if (date != null)
            return DateConverter.convertStringToDate(date.replace("T", " ").replace("Z", ""), "yyyy-MM-dd hh:mm:ss");
        else
            return null;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(amount);
        dest.writeParcelable(user, flags);
        dest.writeString(date);
        dest.writeBooleanArray(new boolean[]{hidden});
    }

    public static final Parcelable.Creator<Bid> CREATOR = new Parcelable.Creator<Bid>() {   // Статический метод с помощью которого создаем обьект
        public Bid createFromParcel(Parcel in) {
            return new Bid(in);
        }

        public Bid[] newArray(int size) {
            return new Bid[size];
        }
    };
}
