package com.hackaton.hevre.clientapplication.Model;

import android.content.Context;
import android.location.Location;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.DB.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class ModelService implements IModelService {

    private Context mContext;
    private AppCallbackActivity activity = null;
    private BusinessController mBusinessController;
    private UserController mUsersController;
    private ProductController mProductController;
    private static ModelService instance;
    private DatabaseAccess mDbTool;

    private ModelService(Context context){
        mContext = context;
        mBusinessController = new BusinessController();
        mUsersController = new UserController();
        mProductController = new ProductController();
        mDbTool = DatabaseAccess.getInstance(context);
        mDbTool.open();

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

    /* static methods */

    /**
     * Return a singleton instance of ModelService.
     *
     * @param context the Context
     * @return the instance of ModelService
     */
    public static ModelService getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new ModelService(context);
        }
        return instance;
    }

    /* methods */

    /* All the contracts appears in the interface file */

    @Override
    public void setDelegate(AppCallbackActivity activity) {
        this.mDbTool.setOpenHelper(activity);
        this.activity = activity;
    }

    @Override
    public void login(String userName, String password) {
        LoginStatus status = mUsersController.login(userName, password);
        activity.loginCallback(status);
    }

    @Override
    public void register(String userName, String password, String email) {
        LoginStatus status = mUsersController.addUser(userName, password, email);
        activity.register_callback(status);
    }

    /* a helper function for static demo only */
    private void addProduct2(String userName, String productName) {
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

        activity.addtask_callback(status);
    }

    @Override
    public void findRelevantBusinesses(String username, Location location) {
        /**
         * 1. get the user
         * 2. get all his tasks
         * 3. collect all the tags from the tasks
         * 4. search for close businesses with the relevant tags
         * 5. push notification with the relevant businesses for the user
         */
        ArrayList<Business> relevantBusinesses = new ArrayList<>();
        relevantBusinesses.add(mBusinessController.getBusinessById(0));
        try
        {
            activity.pushNotification_callback(relevantBusinesses);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void getUserTaskList(String userName) {
        List<String> UserProducts = mUsersController.getUserTaskList(userName);
        activity.UserProducts_callback(UserProducts);
    }

    @Override
    public ArrayList<BusinessRowItem> getBusinessesInRange(double longitude, double latitude, double v) {
        ArrayList<BusinessRowItem> result = new ArrayList();
        result.add(new BusinessRowItem("Apple Store", "Beit Eliezer, Ha'sholtim 5", 34.7923249, 31.2504486));
        return result;
//        return mDbTool.getBusinessesInRange(longitude, latitude, v);
    }

    @Override
    public void closeDb() {
        mDbTool.close();
    }


}
