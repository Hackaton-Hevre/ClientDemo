package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.BusinessManagement.BusinessController;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.BusinessManagement.IBusinessController;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.ProductManagement.IProductController;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.ProductManagement.ProductController;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.UserManagement.IUserController;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.UserManagement.UserController;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.TaskingStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.ServiceLayer.WikiDataService.IWikiDataApi;
import com.hackaton.hevre.clientapplication.Model.Server.ServiceLayer.WikiDataService.WikiDataApiWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class ModelService implements IModelService {

    private Context mContext;
    private AppCallbackActivity activity = null;
    private IBusinessController mBusinessController;
    private IUserController mUsersController;
    private IProductController mProductController;
    private static ModelService instance;

    private ModelService(Context context){
        mContext = context;
        mBusinessController = BusinessController.getInstance(context);
        mUsersController = UserController.getInstance();
        mProductController = ProductController.getInstance();

        /* TODO : remove this static data and replace it with word analyzer */
        // add some static data
        addProduct2("avichay", "אוכל כלבים");
        addProduct2("avichay", "בננה");
        addProduct2("aviv", "דיסק");
        addProduct2("aviv", "לחם");
        addProduct2("ron", "טאבלט");
        addProduct2("ron", "גיבסון");
        addProduct2("moshe", "אקאמול");
        addProduct2("moshe", "חולצת ספורט");
        addProduct2("moshe", "בננה");
        addProduct2("ran", "סנדוויץ'");
        addProduct2("ran", "קפה שחור");
        addProduct2("ran", "בשר");
        addProduct2("itamar", "עפרונות");
        addProduct2("itamar", "נייר טואלט");
        addProduct2("rozbaum", "משחת שיניים");
        addProduct2("rozbaum", "עפרונות");
        addProduct2("rozbaum", "נעלי ריצה");
        addProduct2("10158180273220136", "בשר");
        addProduct2("10158180273220136", "עפרונות");
        addProduct2("10158180273220136", "גיבסון");
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
        this.mBusinessController.setDBOpenHelper(activity);
        this.activity = activity;
    }

    @Override
    public void login(String userName, String password) {
        LoginStatus status = mUsersController.login(userName, password);
        activity.loginCallback(status);
    }

    @Override
    public void facebookLogin(String facebookKey, String email) {
        LoginStatus status = mUsersController.facebookLogin(facebookKey, email);
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
    public List<String> addProduct(final String userName, String product) {

        IWikiDataApi wikiDataApi = WikiDataApiWrapper.getInstance();
        List<String> categories = wikiDataApi.getAllTags(product);
        return addProduct_afterResponse(userName, product, categories);

    }

    private List<String> addProduct_afterResponse(String userName, String productName, List<String> categories) {
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

        categories.add(status.toString());

        return categories;
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
            Log.e("ModelService", e.getStackTrace().toString());
        }
    }

    @Override
    public void getUserTaskList(String userName) {
        List<String> UserProducts = mUsersController.getUserTaskList(userName);
        activity.UserProducts_callback(UserProducts);
    }

    @Override
    public ArrayList<Business> getBusinessesInRange(double longitude, double latitude, double v) {
        ArrayList<Business> result = new ArrayList();
//        result.add(new Business("Apple Store", "Beit Eliezer, Ha'sholtim 5", 34.7923249, 31.2504486));
        return result;
//        return mDbTool.getBusinessesInRange(longitude, latitude, v);
    }

    @Override
    public void closeDb() {
        //mDbTool.close();
    }


}
