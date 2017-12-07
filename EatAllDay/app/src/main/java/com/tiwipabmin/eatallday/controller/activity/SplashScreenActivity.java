package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tiwipabmin.eatallday.EatAllDayPreferences;
import com.tiwipabmin.eatallday.R;
import com.tiwipabmin.eatallday.api.UserApi;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;
import com.tiwipabmin.eatallday.model.User;
import com.tiwipabmin.eatallday.validation.CheckNetworkConnection;

public class SplashScreenActivity extends AppCompatActivity
implements ValueEventListener{

    private Handler mHandler;
    private Runnable mRunnable;
    private long mDelayTime;
    private long mTime = 3000L;
    private User mUser;
    private ProgressDialogFragment mProgress;
    private CheckNetworkConnection mCheckNetworkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mHandler = new Handler();

        mCheckNetworkConnection = CheckNetworkConnection
                .getCheckNetworkConnection();

        mRunnable = new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = EatAllDayPreferences.getSharedPreferences(SplashScreenActivity.this);
                if(sharedPreferences.getString(EatAllDayPreferences.PREF_USER_NAME, "null").equals("null")
                        || !mCheckNetworkConnection.isConnected()) {

                    Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    mProgress = new ProgressDialogFragment();
                    mProgress.show(getSupportFragmentManager(), "progress");

                    UserApi.getUserApi()
                            .getChildUser()
                            .orderByChild("userId")
                            .equalTo(sharedPreferences.getString(EatAllDayPreferences.PREF_USER_NAME, "null"))
                            .addListenerForSingleValueEvent(SplashScreenActivity.this);

                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelayTime = mTime;
        mHandler.postDelayed(mRunnable, mDelayTime);
        mTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
        mTime = mDelayTime - (System.currentTimeMillis() - mTime);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.getValue(User.class);
            mUser.setUserId(ds.getKey());
        }

        if (mUser != null) {

            mProgress.dismiss();
            Intent intent = new Intent(SplashScreenActivity.this, TabBarActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
            finish();

        }

        if (mProgress.isVisible()) {
            mProgress.dismiss();
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
