package kmitl.it13.millibear.eatallday.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kmitl.it13.millibear.eatallday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialRoomFragment extends Fragment {


    public SocialRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_room, container, false);
    }

}