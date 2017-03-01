package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.ProductManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IProductController {

    Product getProductByName(String productName);

}
