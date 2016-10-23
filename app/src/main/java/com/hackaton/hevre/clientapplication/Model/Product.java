package com.hackaton.hevre.clientapplication.Model;

/**
 * Created by אביחי on 23/10/2016.
 */
public class Product {

    private int id;
    private Tag tag;
    private String name;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Product(int id , Tag tag, String inName)
    {
        this.id = id;
        this.name = inName;
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
