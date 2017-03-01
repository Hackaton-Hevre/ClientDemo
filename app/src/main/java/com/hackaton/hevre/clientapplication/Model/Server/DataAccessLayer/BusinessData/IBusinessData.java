package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.BusinessData;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IBusinessData {

    Business getBusinessById(int id);

    void setDBOpenHelper(AppCallbackActivity activity);

}
