package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement;

import android.location.Location;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 * follows Builder design pattern
 */
public class Business {

    /* data members */
    private final String id;
    private final String name;
    private List<Tag> tags;
    private final Location location;
    private final String address;


    /**
     * C-tor which has to get builder object and be built for whithin it. shouldn't be used directly.
     *
     * @param builder creates this class.
     */
    private Business(BusinessBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.tags = builder.tags;
        this.location = builder.location;
        this.address = builder.address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Location getLocation() {
        return location;
    }

    public double getLat() {
        return this.location.getLatitude();
    }

    public double getLng() {
        return this.location.getLongitude();
    }

    public String getAddress() {
        return this.address;
    }

    public static class BusinessBuilder {

        /* data members */
        private String id;
        private String name;
        private List<Tag> tags;
        private Location location;
        private String address;

        /**
         * default C-tor
         */
        public BusinessBuilder() {

        }

        public BusinessBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BusinessBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public BusinessBuilder setTypes(String[] tags) {
            this.tags = new ArrayList<>();
            return this;
        }

        public BusinessBuilder setTypes(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public BusinessBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public BusinessBuilder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public BusinessBuilder setLocation(double lat, double lng) {
            Location location = new Location(this.name);
            location.setLatitude(lat);
            location.setLongitude(lng);
            this.location = location;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }

}
