package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.BusinessManagement;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IBusinessController {

    void setDBOpenHelper(AppCallbackActivity activity);

    Business getBusinessById(int id);
}
