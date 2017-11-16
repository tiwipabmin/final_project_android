package kmitl.it13.millibear.eatallday.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendApi {

    private static FriendApi friendApi;
    private DatabaseReference childFriend;

    private FriendApi() {

        childFriend = FirebaseDatabase.getInstance().getReference().child("friend");
    }

    public static FriendApi getFriendApi(){
        if(friendApi == null){
            friendApi = new FriendApi();
        }
        return friendApi;
    }

    public DatabaseReference getChildFriend() {
        return childFriend;
    }
}
