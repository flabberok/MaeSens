package by.maesens.android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Comparable, Parcelable {

    private boolean fromServer;
    private int id;
    private LocationParentResource parentResource;
    private String reqParams;
    private boolean restangularCollection;
    private boolean restangularized;
    private String route;
    private String title;

    public Location(){}

    public boolean isFromServer() {
        return fromServer;
    }

    public void setFromServer(boolean fromServer) {
        this.fromServer = fromServer;
    }

    public LocationParentResource getParentResource() {
        return parentResource;
    }

    public void setParentResource(LocationParentResource parentResource) {
        this.parentResource = parentResource;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public boolean isRestangularCollection() {
        return restangularCollection;
    }

    public void setRestangularCollection(boolean restangularCollection) {
        this.restangularCollection = restangularCollection;
    }

    public boolean isRestangularized() {
        return restangularized;
    }

    public void setRestangularized(boolean restangularized) {
        this.restangularized = restangularized;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    private Location(Parcel parcel) {  // Создание объекта через Parcel
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
        return title;
    }

    @Override
    public int compareTo(Object another) {
        return title.compareTo(another.toString());
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

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {   // Статический метод с помощью которого создаем обьект
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
