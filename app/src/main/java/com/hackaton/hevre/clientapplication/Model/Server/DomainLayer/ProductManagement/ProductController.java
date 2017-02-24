package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement;

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

        addProduct(nextId(), Tag.TAG_PETS, "אוכל כלבים");
        addProduct(nextId(), Tag.TAG_FOOD, "בננה");
        addProduct(nextId(), Tag.TAG_PHARM, "אקמול");
        addProduct(nextId(), Tag.TAG_SPORT, "נעלי ריצה");
        addProduct(nextId(), Tag.TAG_MUSIC, "גיבסון");
        addProduct(nextId(), Tag.TAG_FOOD, "קפה שחור");
        addProduct(nextId(), Tag.TAG_PETS, "פעמון חתול");
        addProduct(nextId(), Tag.TAG_MUSIC, "דיסק");
        addProduct(nextId(), Tag.TAG_FOOD, "לחם");
        addProduct(nextId(), Tag.TAG_PHARM, "משחת שיניים");
        addProduct(nextId(), Tag.TAG_FOOD, "בשר");
        addProduct(nextId(), Tag.TAG_SPORT, "חולצת ריצה");
        addProduct(nextId(), Tag.TAG_ELECTRONIC, "טאבלט");
        addProduct(nextId(), Tag.TAG_RESTURANTS, "סנדוויץ'");
        addProduct(nextId(), Tag.TAG_PHARM, "נייר טואלט");
        addProduct(nextId(), Tag.TAG_OFFICE_SUPPLY, "עפרונות");
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
