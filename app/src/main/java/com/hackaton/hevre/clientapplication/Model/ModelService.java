package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.location.Location;

import com.hackaton.hevre.clientapplication.Controller.HomeActivity;
import com.hackaton.hevre.clientapplication.Controller.MainActivity;
import com.hackaton.hevre.clientapplication.Controller.RegisterActivity;

import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class ModelService implements IModelService {

    private Activity activity = null;
    private BusinessController mBusinessController;
    private UserController mUsersController;
    private ProductController mProductController;
    private static ModelService instance = new ModelService();

    private ModelService(){
        mBusinessController = new BusinessController();
        mUsersController = new UserController();
        mProductController = new ProductController();

        // add some static data
        addProduct2("avichay", "Akana");
        addProduct2("avichay", "Banana");
        addProduct2("aviv", "Disk");
        addProduct2("aviv", "Bread");
        addProduct2("ron", "Tablet");
        addProduct2("ron", "Gibson");
        addProduct2("moshe", "Acamol");
        addProduct2("moshe", "Sport shirt");
        addProduct2("moshe", "Banana");
        addProduct2("ran", "Sandwich");
        addProduct2("ran", "Black coffee");
        addProduct2("ran", "Meat");
        addProduct2("itamar", "Pencils");
        addProduct2("itamar", "Toalet paper");
        addProduct2("rozbaum", "Tooth paste");
        addProduct2("rozbaum", "Pencils");
        addProduct2("rozbaum", "Running shoes");
    }

    public static ModelService getInstance()
    {
        return instance;
    }

    @Override
    public void setDelegate(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void login(String userName, String password) {
        LoginStatus status = mUsersController.login(userName, password);
        ((MainActivity) activity).loginCallback(status);
    }

    @Override
    public void register(String userName, String password, String email) {
        LoginStatus status = mUsersController.addUser(userName, password, email);
        ((RegisterActivity) activity).register_callback(status);
    }

    public void addProduct2(String userName, String productName) {
        Product product = mProductController.getProductByName(productName);
        mUsersController.addProduct(userName, product);
    }

    @Override
    public void addProduct(String userName, String productName) {
        TaskingStatus status = TaskingStatus.SUCCESS;

        Product product = mProductController.getProductByName(productName);
        if (product == null)
        {
            status = TaskingStatus.ILLEGAL_TASK;
        }
         else if (product != null)
        {
           boolean isAdded = mUsersController.addProduct(userName, product);
            if(!isAdded)
            {
                status = TaskingStatus.TASK_ALREADY_EXISTS;
            }
        }

        ((HomeActivity) activity).addtask_callback(status);
    }

    @Override
    public void findReleventBusinesses(String username, Location location) {

    }

    @Override
    public void getUserTaskList(String userName) {
        List<String> UserProducts = mUsersController.getUserTaskList(userName);
        ((HomeActivity) activity).UserProducts_callback(UserProducts);
    }


}
