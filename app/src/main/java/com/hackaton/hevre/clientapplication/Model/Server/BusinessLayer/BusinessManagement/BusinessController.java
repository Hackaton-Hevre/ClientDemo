package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.BusinessManagement;

import android.content.Context;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.BusinessData.BusinessData;
import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.BusinessData.IBusinessData;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;

/**
 * Created by אביחי on 23/10/2016.
 */
public class BusinessController implements IBusinessController {

    private static BusinessController instance;
    private IBusinessData mBusinessData;

    public static BusinessController getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new BusinessController(context);
        }
        return instance;
    }

    private BusinessController(Context context) {
        mBusinessData = BusinessData.getInstance(context);
    }

    public void setDBOpenHelper(AppCallbackActivity activity)
    {
        mBusinessData.setDBOpenHelper(activity);
    }

    public Business getBusinessById(int id)
    {
        Business b = mBusinessData.getBusinessById(id);

        if (b == null)
        {
            // TODO : add support for informative ERROR MESSAGES
        }

        return b;
    }

}
