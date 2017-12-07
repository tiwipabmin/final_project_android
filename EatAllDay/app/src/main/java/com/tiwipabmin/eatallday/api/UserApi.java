package com.tiwipabmin.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiwipabmin.eatallday.controller.activity.TabBarActivity;

import com.tiwipabmin.eatallday.model.User;

public class UserApi {

    private static UserApi mUserApi;
    private DatabaseReference mChildUser;

    private UserApi(){

        mChildUser = FirebaseDatabase.getInstance().getReference("user");
    }

    public static UserApi getUserApi() {
        if(mUserApi == null){
            mUserApi = new UserApi();
        }
        return mUserApi;
    }

    public DatabaseReference getChildUser() {
        return mChildUser;
    }



    public void newUser(Context context, User newUser){
        mChildUser.child(newUser.getUserId()).setValue(newUser);
    }

    public void updateUser(User updateUser){
        mChildUser.child(updateUser.getUserId()).setValue(updateUser);
    }

    public void deleteProfileImage() {
        mChildUser.child(TabBarActivity.USER.getUserId())
                .child("image")
                .setValue("https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/User-info.svg/1024px-User-info.svg.png");
    }

    public void editProfileImage(Context context, String profileImage) {
        mChildUser.child(TabBarActivity.USER.getUserId())
                .child("image")
                .setValue(profileImage);
        Toast.makeText(context, "เปลี่ยนรูปภาพโปรไฟล์เรียบร้อยแล้วจ้าาา.", Toast.LENGTH_SHORT).show();
    }
}
