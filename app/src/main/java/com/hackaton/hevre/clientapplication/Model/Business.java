package com.hackaton.hevre.clientapplication.Model;

import android.location.Location;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class Business {

    private int id;
    private String name;
    private List<Tag> tags;
    private Location location;

    public Business(int inId, String inName, List<Tag> inTags, Location inLocation)
    {
        id = inId;
        name = inName;
        tags = inTags;
        location = inLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
