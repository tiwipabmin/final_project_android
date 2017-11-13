package kmitl.it13.millibear.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import kmitl.it13.millibear.eatallday.model.User;

public class UserApi {

    public interface UserApiListener {

        public void userIdResponse(long lastUserId);
    }

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

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user/" + newUser.getUserId(), newUser);

        Toast.makeText(context, newUser.getUserId(), Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}
