package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.ProductData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IProductData {

    Product getProductByName(String productName);

}
