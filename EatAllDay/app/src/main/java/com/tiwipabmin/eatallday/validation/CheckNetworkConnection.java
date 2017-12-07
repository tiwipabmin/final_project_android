package com.tiwipabmin.eatallday.validation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckNetworkConnection {
    public static CheckNetworkConnection mCheckNetworkConnection;
    private boolean mIsConnected = false;

    private CheckNetworkConnection(){

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);

                if (connected) {
                    mIsConnected = true;
                } else {
                    mIsConnected = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    public static CheckNetworkConnection getCheckNetworkConnection() {
        if(mCheckNetworkConnection == null){
            mCheckNetworkConnection = new CheckNetworkConnection();
        }
        return mCheckNetworkConnection;
    }

    public boolean isConnected() {
        return mIsConnected;
    }
}
