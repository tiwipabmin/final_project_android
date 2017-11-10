package kmitl.it13.millibear.eatallday.api;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserApi implements ValueEventListener{

    public interface UserApiListener {

        public void userIdResponse(long lastUserId);
    }

    private static UserApi userApi;
    private DatabaseReference childUser;
    private UserApiListener listener;

    public void setListener(UserApiListener listener) {
        this.listener = listener;
    }

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



//    public void newUser(String userId, String name, String email, String password, String facebook){
//        User user = new User(name, email, password, facebook);
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/user/" + Long.valueOf(userId), user);
//
//        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
//    }

    /*
    * fetch last id from child user
    */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        long lastUserId = 0;

//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            if(Long.valueOf(ds.getKey()) != 0){
//                lastUserId = Long.valueOf(ds.getKey()) + 1;
//            }
//        }

//        listener.userIdResponse(lastUserId);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
