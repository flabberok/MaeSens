package by.maesens.android.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Никита on 13.02.2016.
 */
public class Category implements Parcelable {
    private int id;
    private String title;

    public Category() {}

    private Category(Parcel parcel) {  // Создание объекта через Parcel
        id = parcel.readInt();
        title = parcel.readString();
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

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {   // Статический метод с помощью которого создаем обьект
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
