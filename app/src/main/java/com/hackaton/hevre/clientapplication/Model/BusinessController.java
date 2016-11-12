package com.hackaton.hevre.clientapplication.Model;

import android.location.Location;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class BusinessController {

    private HashMap<String, Business> mBusinesses;
    private static int mIdCounter = -1;



    public BusinessController() {
        mBusinesses = new HashMap<>();

        Location location;
        LinkedList<Tag> tags;
        String name;

        // make some static businesses data

        name = "AmitPharm";
        location = new Location(name);
        location.setLatitude(32.414133);
        location.setLongitude(34.951222);
        tags = new LinkedList<>();
        tags.add(Tag.TAG_PHARM);
        addBusiness(nextId() + "", name, location, tags);

//        name = "AvichayCoffee";
//        location = new Location(name);
//        location.setLatitude(32.414168);
//        location.setLongitude(34.950169);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_RESTURANTS);
//        addBusiness(nextId(), name, location, tags);
//
//        name = "AssafGym";
//        location = new Location(name);
//        location.setLatitude(32.475198);
//        location.setLongitude(35.001172);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_SPORT);
//        addBusiness(nextId(), name, location, tags);
//
//        name = "RonGuitars";
//        location = new Location(name);
//        location.setLatitude(32.416928);
//        location.setLongitude(34.946555);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_MUSIC);
//        tags.add(Tag.TAG_ELECTRONIC);
//        addBusiness(nextId(), name, location, tags);
//
//        name = "MoshePets";
//        location = new Location(name);
//        location.setLatitude(32.434854);
//        location.setLongitude(34.918492);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_PETS);
//        addBusiness(nextId(), name, location, tags);
//
//        name = "GhaniHadarimMall";
//        location = new Location(name);
//        location.setLatitude(32.415744);
//        location.setLongitude(34.951930);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_PETS);
//        tags.add(Tag.TAG_SPORT);
//        tags.add(Tag.TAG_FOOD);
//        tags.add(Tag.TAG_PHARM);
//        tags.add(Tag.TAG_ELECTRONIC);
//        tags.add(Tag.TAG_OFFICE_SUPPLY);
//        tags.add(Tag.TAG_RESTURANTS);
//        addBusiness(nextId(), name, location, tags);
//
//        name = "GalulasSportsAndSuperAndOffice";
//        location = new Location(name);
//        location.setLatitude(32.415744);
//        location.setLongitude(34.951930);
//        tags = new LinkedList<>();
//        tags.add(Tag.TAG_SPORT);
//        tags.add(Tag.TAG_FOOD);
//        tags.add(Tag.TAG_OFFICE_SUPPLY);
//        addBusiness(nextId(), name, location, tags);

    }

    public boolean addBusiness(String bId, String bName, Location bLocation, List<Tag> bTags)
    {
        boolean isAdded = false;

        if (!mBusinesses.containsKey(bId))
        {
            Business business = new Business.BusinessBuilder().setId(bId).setName(bName).setLocation(bLocation).setTypes(bTags).build();
            mBusinesses.put(bId, business);
            isAdded = true;
        }

        return isAdded;
    }

    public Business getBusinessById(int id)
    {
        Business b = null;

        if (mBusinesses.containsKey(id + ""))
        {
            b = mBusinesses.get(id + "");
        }

        return b;
    }

    private int nextId()
    {
        mIdCounter++;
        return mIdCounter;
    }

}
