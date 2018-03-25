package com.peacecorps.malaria.user_profile;

import android.view.View;

/**
 * Created by arundhati on 25/3/18.
 */

public interface UserProfilePresenter {

    public View.OnClickListener validateCredentials(String name, String email, String age);
    public void onDestroy();
}
