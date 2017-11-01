package kmitl.it13.millibear.eatallday.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tiwip on 11/1/2017.
 */

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



    public void newUser(String userId, String name, String email, String password, String facebook){
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("password", password);
        user.put("facebook", facebook);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user/" + Long.valueOf(userId), user);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}
