package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.ProductManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.ProductData.IProductData;
import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.ProductData.ProductData;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

/**
 * Created by אביחי on 23/10/2016.
 */
public class ProductController implements IProductController {

    private static ProductController instance = null;
    private IProductData mProductData;

    public static ProductController getInstance()
    {
        if (instance == null)
        {
            instance = new ProductController();
        }
        return instance;
    }

    private ProductController()
    {
        mProductData = ProductData.getInstance();
    }

    public Product getProductByName(String productName) {
        Product product = mProductData.getProductByName(productName);

        if (product == null)
        {
            // TODO : add support for informative ERROR MESSAGES
        }

        return product;
    }
}
