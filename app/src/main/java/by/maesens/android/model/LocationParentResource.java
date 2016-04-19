package by.maesens.android.model;

/**
 * Created by Никита on 31.03.2016.
 */
public class LocationParentResource {
    private String id;
    private String parentResource;
    private String route;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentResource() {
        return parentResource;
    }

    public void setParentResource(String parentResource) {
        this.parentResource = parentResource;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
