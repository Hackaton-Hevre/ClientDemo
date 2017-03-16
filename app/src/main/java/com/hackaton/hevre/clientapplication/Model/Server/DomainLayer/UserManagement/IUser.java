package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by אביחי on 13/03/2017.
 */

public abstract class IUser {

    protected LinkedList<Product> mActiveProducts;
    protected LinkedList<Product> mInActiveProducts;
    protected ICredentials mCredentials;

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

}
