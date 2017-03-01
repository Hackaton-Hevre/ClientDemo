package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.ProductData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Tag;

import java.util.HashMap;

/**
 * Created by אביחי on 01/03/2017.
 */

public class ProductData implements IProductData {

    // product name to product
    HashMap<String, Product> mProducts;
    private int mIdCounter = -1;

    private static ProductData instance = null;

    public static ProductData getInstance()
    {
        if (instance == null)
        {
            instance = new ProductData();
        }
        return instance;
    }

    private ProductData()
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

    public Product getProductByName(String productName) {
        Product product = null;

        if (mProducts.containsKey(productName.toLowerCase()))
        {
            product = mProducts.get(productName.toLowerCase());
        }

        return product;
    }

    private boolean addProduct(int id, Tag tag, String name) {
        boolean isAdded = false;

        if (!mProducts.containsKey(name.toLowerCase()))
        {
            Product product = new Product(id, tag, name);
            mProducts.put(name, product);
            isAdded = true;
        }

        return isAdded;
    }

    private int nextId()
    {
        mIdCounter++;
        return mIdCounter;
    }

}
