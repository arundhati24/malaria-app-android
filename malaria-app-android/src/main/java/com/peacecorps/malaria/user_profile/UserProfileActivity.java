package com.peacecorps.malaria.user_profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peacecorps.malaria.R;
import com.peacecorps.malaria.activities.InfoHubFragmentActivity;
import com.peacecorps.malaria.activities.MainActivity;
import com.peacecorps.malaria.activities.NewHomeActivity;
import com.peacecorps.malaria.activities.TripIndicatorFragmentActivity;
import com.peacecorps.malaria.interfaces.GetUserCallback;
import com.peacecorps.malaria.model.AppUserModel;
import com.peacecorps.malaria.model.SharedPreferenceStore;
import com.peacecorps.malaria.utils.ServerRequests;


public class UserProfileActivity extends Activity implements UserProfileView{
    private EditText userNameEt;
    private EditText userEmailEt;
    private EditText userAgeEt;
    private EditText userMedicineTypeEt;
    private Button saveData;
    private Button homeIconButton;
    private Button btnTripIndicator;
    private Button infoHub;
    private Button newHomeButton;
    private String userMedicineType;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private UserProfilePresenter mUserProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        userNameEt = (EditText)findViewById(R.id.user_name);
        userEmailEt = (EditText)findViewById(R.id.user_email);
        userAgeEt = (EditText)findViewById(R.id.user_age);
        userMedicineTypeEt = (EditText)findViewById(R.id.user_medicine_type);
        saveData = (Button)findViewById(R.id.user_profile_save);

        //footer buttons
        homeIconButton = (Button) findViewById(R.id.homeButton);
        btnTripIndicator = (Button) findViewById(R.id.tripButton);
        infoHub = (Button) findViewById(R.id.infoButton);
        newHomeButton = (Button)findViewById(R.id.tempButton);

        mUserProfilePresenter = new UserProfilePresenterImpl(this);

        homeIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(getApplication().getApplicationContext(), MainActivity.class);
            }
        });
        btnTripIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(getApplication().getApplicationContext(),
                        TripIndicatorFragmentActivity.class);
            }
        });
        infoHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(getApplication().getApplicationContext(),
                        InfoHubFragmentActivity.class);
            }
        });
        newHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToActivity(getApplication().getApplicationContext(),
                        NewHomeActivity.class);
            }
        });
        //footer ends


        getPreviousDetails();
        saveData.setOnClickListener(mUserProfilePresenter
                .validateCredentials(userNameEt.toString(),
                userEmailEt.toString(), userAgeEt.toString()));
    }

    //fetch previously entered details if any
    public void getPreviousDetails(){
        userMedicineType = SharedPreferenceStore.mPrefsStore
                .getString("com.peacecorps.malaria.drugPicked", null);
        userMedicineTypeEt.setText(userMedicineType);

        sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("user_name","");
        String userEmail = sharedPreferences.getString("user_email","");
        int userAge = sharedPreferences.getInt("user_age",0);
        userNameEt.setText(userName);
        userEmailEt.setText(userEmail);
        if (userAge == 0) {
            userAgeEt.setText("");
        }
        else {
            userAgeEt.setText("" + userAge);
        }
    }

    //save new values to shared preferences
    public void setNewDetails(){
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        editor = sharedPreferences.edit();
        editor.putString("user_name",userNameEt.getText().toString());
        editor.putString("user_email", userEmailEt.getText().toString());
        editor.putInt("user_age", Integer.parseInt(userAgeEt.getText().toString()));
        editor.commit();
    }

    @Override
    public void setNameError() {
        userNameEt.setError("Name required");
    }

    @Override
    public void setEmailError() {
        userEmailEt.setError("Valid Email required");
    }

    @Override
    public void setAgeError() {
        userAgeEt.setError("Age required");
    }

    @Override
    public void navigateToActivity(Context context, Class newClass) {
        startActivity(new Intent(context, newClass));
        finish();
    }

    //create the server request
    public void postUserDetails(AppUserModel user){
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(String status) {
                if(status.equals("200")){
                    setNewDetails();
                    Toast.makeText(UserProfileActivity.this,
                            "User Details submitted", Toast.LENGTH_SHORT).show();
                    UserProfileActivity.this.finish();
                }
                else{
                    Toast.makeText(UserProfileActivity.this,
                            "Failed! Please try again after some time.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}