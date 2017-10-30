package kmitl.it13.millibear.eatallday.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tiwip on 10/29/2017.
 */

public class ConnectServer {

    private static ConnectServer mConnectServer;
    private DatabaseReference mDatabase;

    private ConnectServer() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static ConnectServer getConnectServer() {
        if(mConnectServer == null){
            mConnectServer = new ConnectServer();
        }
        return mConnectServer;
    }

    public DatabaseReference getDatabase() {
        return mDatabase;
    }

    public void setDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }
}
