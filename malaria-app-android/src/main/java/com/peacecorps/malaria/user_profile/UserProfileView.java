package com.peacecorps.malaria.user_profile;

import android.content.Context;

import com.peacecorps.malaria.model.AppUserModel;

/**
 * Created by arundhati on 25/3/18.
 */

public interface UserProfileView {

    public void getPreviousDetails();
    public void setNameError();
    public void setEmailError();
    public void setAgeError();
    public void navigateToActivity(Context context, Class newClass);
    public void postUserDetails(AppUserModel user);

}
