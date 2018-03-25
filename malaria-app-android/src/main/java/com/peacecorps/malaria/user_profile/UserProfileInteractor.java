package com.peacecorps.malaria.user_profile;

import android.view.View;

/**
 * Created by arundhati on 25/3/18.
 */

public interface UserProfileInteractor {

    interface onSaveDataFinisedListener {

        public void checkNameValidity();
        public void checkEmailValidity();
        public void checkAgeVailidity();
        public void onSuccess();

    }

    public View.OnClickListener saveDataSetOnClickListener(String name, String email, String age, onSaveDataFinisedListener listener);
}
