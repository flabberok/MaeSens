package by.maesens.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Никита on 17.03.2016.
 */
public class Winner implements Parcelable{
    private User user;
    private int amount;
    private boolean paid;
    private boolean hidden;

    private Winner(Parcel parcel) {
        amount = parcel.readInt();
        paid = (parcel.readByte() != 0);
        hidden = (parcel.readByte() != 0);
        user = parcel.readParcelable(User.class.getClassLoader());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isHidden() {
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
        dest.writeInt(amount);
        dest.writeByte((byte) (paid ? 1 : 0));
        dest.writeByte((byte) (hidden ? 1 : 0));
        dest.writeParcelable(user, flags);
    }

    public static final Parcelable.Creator<Winner> CREATOR = new Parcelable.Creator<Winner>() {   // Статический метод с помощью которого создаем обьект
        public Winner createFromParcel(Parcel in) {
            return new Winner(in);
        }

        public Winner[] newArray(int size) {
            return new Winner[size];
        }
    };
}
