package com.hackaton.hevre.clientapplication.Model;

import java.util.HashMap;

/**
 * Created by אביחי on 23/10/2016.
 */
public class ProductController {

    // product name to product
    HashMap<String, Product> mProducts;
    private int mIdCounter = -1;

    public ProductController()
    {
        mProducts = new HashMap<>();

        addProduct(nextId(), Tag.TAG_PETS, "akana");
        addProduct(nextId(), Tag.TAG_FOOD, "banana");
        addProduct(nextId(), Tag.TAG_PHARM, "acamol");
        addProduct(nextId(), Tag.TAG_SPORT, "running shoes");
        addProduct(nextId(), Tag.TAG_MUSIC, "gibson");
        addProduct(nextId(), Tag.TAG_FOOD, "black coffee");
        addProduct(nextId(), Tag.TAG_PETS, "cat bell");
        addProduct(nextId(), Tag.TAG_MUSIC, "disk");
        addProduct(nextId(), Tag.TAG_FOOD, "bread");
        addProduct(nextId(), Tag.TAG_PHARM, "tooth paste");
        addProduct(nextId(), Tag.TAG_FOOD, "meat");
        addProduct(nextId(), Tag.TAG_SPORT, "sport shirt");
        addProduct(nextId(), Tag.TAG_ELECTRONIC, "tablet");
        addProduct(nextId(), Tag.TAG_RESTURANTS, "sandwich");
        addProduct(nextId(), Tag.TAG_PHARM, "toalet paper");
        addProduct(nextId(), Tag.TAG_OFFICE_SUPPLY, "pencils");
    }

    public boolean addProduct(int id, Tag tag, String name) {
        boolean isAdded = false;

        if (!mProducts.containsKey(name.toLowerCase()))
        {
            Product product = new Product(id, tag, name);
            mProducts.put(name, product);
            isAdded = true;
        }

        return isAdded;
    }

    public Product getProductByName(String productName) {
        Product product = null;

        if (mProducts.containsKey(productName.toLowerCase()))
        {
            product = mProducts.get(productName.toLowerCase());
        }

        return product;
    }

    private int nextId()
    {
        mIdCounter++;
        return mIdCounter;
    }
}
