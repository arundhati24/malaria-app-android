package com.peacecorps.malaria.user_profile;

import android.view.View;

import com.peacecorps.malaria.model.AppUserModel;

/**
 * Created by arundhati on 25/3/18.
 */

public class UserProfilePresenterImpl implements UserProfilePresenter, UserProfileInteractor.onSaveDataFinisedListener {

    UserProfileView mUserProfileView;
    UserProfileInteractor mUserProfileInteractor;

    public UserProfilePresenterImpl(UserProfileView userProfileView){
        mUserProfileView = userProfileView;
        mUserProfileInteractor = new UserProfileInteractorImpl();
    }

    @Override
    public View.OnClickListener validateCredentials(String name, String email, String age) {
        if(mUserProfileView != null) {
            return mUserProfileInteractor.saveDataSetOnClickListener(name, email, age, this);
        }
        return null;
    }

    @Override
    public void onDestroy() {
        if(mUserProfileView != null){
            mUserProfileView = null;
        }
    }

    @Override
    public void checkNameValidity() {
        if(mUserProfileView != null){
            mUserProfileView.setNameError();
        }
    }

    @Override
    public void checkEmailValidity() {
        if (mUserProfileView != null){
            mUserProfileView.setEmailError();
        }

    }

    @Override
    public void checkAgeVailidity() {
        if (mUserProfileView != null){
            mUserProfileView.setAgeError();
        }

    }

    @Override
    public void onSuccess() {
        AppUserModel user = new AppUserModel();
        mUserProfileView.postUserDetails(user);
    }

}
