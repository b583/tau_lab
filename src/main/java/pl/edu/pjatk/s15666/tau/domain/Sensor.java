package pl.edu.pjatk.s15666.tau.domain;

import pl.edu.pjatk.s15666.tau.database.DbObject;

public class Sensor implements DbObject {

    private int id;
    private boolean isOutdoor;
    private String location;

    public Sensor(String location, boolean isOutdoor) {
        this.location = location;
        this.isOutdoor = isOutdoor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOutdoor() {
        return isOutdoor;
    }

    public void setOutdoor(boolean outdoor) {
        isOutdoor = outdoor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
