package kmitl.it13.millibear.eatallday.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kmitl.it13.millibear.eatallday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyRoomFragment extends Fragment {


    public LobbyRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lobby_room, container, false);
    }

}