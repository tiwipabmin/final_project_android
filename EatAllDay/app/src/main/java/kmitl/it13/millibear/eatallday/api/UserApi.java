package kmitl.it13.millibear.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.model.User;

public class UserApi {

    private static UserApi userApi;
    private DatabaseReference childUser;

    private UserApi(){

        childUser = FirebaseDatabase.getInstance().getReference("user");
    }

    public static UserApi getUserApi() {
        if(userApi == null){
            userApi = new UserApi();
        }
        return userApi;
    }

    public DatabaseReference getChildUser() {
        return childUser;
    }



    public void newUser(Context context, User newUser){
        childUser.child(newUser.getUserId()).setValue(newUser);
    }

    public void updateUser(User updateUser){
        childUser.child(updateUser.getUserId()).setValue(updateUser);
    }

    public void deleteProfileImage() {
        childUser.child(TabBarActivity.USER.getUserId())
                .child("image")
                .setValue("https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/User-info.svg/1024px-User-info.svg.png");
    }

    public void editProfileImage(Context context, String profileImage) {
        childUser.child(TabBarActivity.USER.getUserId())
                .child("image")
                .setValue(profileImage);
        Toast.makeText(context, "เปลี่ยนรูปภาพโปรไฟล์เรียบร้อยแล้วจ้าาา.", Toast.LENGTH_SHORT).show();
    }
}
