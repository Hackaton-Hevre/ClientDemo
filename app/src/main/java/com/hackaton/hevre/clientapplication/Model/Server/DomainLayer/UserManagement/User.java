package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by אביחי on 23/10/2016.
 */
public class User {

    private LinkedList<Product> mActiveProducts;
    private LinkedList<Product> mInActiveProducts;
    private Credentials mCredentials;

    public User (String UserName, String Password, String Email){
        mCredentials = new Credentials(UserName, Password, Email);
        mActiveProducts = new LinkedList<Product>();
        mInActiveProducts = new LinkedList<Product>();
    }

    public boolean user_addProduct(Product p)
    {
        try
        {
            if (mActiveProducts.contains(p))
            {
                return false;
            }
            mActiveProducts.add(p);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public LinkedList<Product> user_getProductList(){
        return mActiveProducts;
    }

    public boolean checkCredentials(String password) {
        return mCredentials.checkCredentials(password);
    }

    public List<String> getStringProducts() {
        List<String> products = transformListProductsToListString(mActiveProducts);
        return products;
    }

    private List<String> transformListProductsToListString(LinkedList<Product> activeProducts) {
        List<String> transformed = new LinkedList<>();
        for (Product prod : activeProducts)
        {
            if (prod != null)
            {
                String Tstr = prod.getName().toString();
                transformed.add(Tstr);
            }
        }
        return transformed;
    }
}
