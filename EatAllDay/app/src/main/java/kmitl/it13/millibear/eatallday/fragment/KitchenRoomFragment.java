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
public class KitchenRoomFragment extends Fragment {


    public KitchenRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitchen_room, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

}
