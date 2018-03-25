package com.peacecorps.malaria.user_profile;

import android.view.View;

import com.peacecorps.malaria.utils.UtilityMethods;

/**
 * Created by arundhati on 25/3/18.
 */

public class UserProfileInteractorImpl implements UserProfileInteractor {
    @Override
    public View.OnClickListener saveDataSetOnClickListener(final String name, final String email, final String age, final onSaveDataFinisedListener listener) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.trim().equals("")){
                    listener.checkNameValidity(); //setError("Name required");
                }
                else if(email.trim().equals("") || !UtilityMethods.validEmail(email)){
                    listener.checkEmailValidity(); //setError("Valid Email required");
                }
                else if(age.trim().equals("") || age.matches("[0]+")){
                    listener.checkAgeVailidity(); //.setError("Age required");
                }
                else{
                    listener.onSuccess();

                    //create object to send
                    //AppUserModel user = new AppUserModel();
                    //get medicine type from shared preferences
                    //user = user.getAppUser(name,email, Integer.parseInt(age),userMedicineType);
                    //postUserDetails(user);
                }

            }
        };
    }
}
