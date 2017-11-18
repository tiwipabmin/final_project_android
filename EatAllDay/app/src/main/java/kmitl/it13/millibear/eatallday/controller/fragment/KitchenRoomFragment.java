package kmitl.it13.millibear.eatallday.controller.fragment;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.controller.activity.AddFoodActivity;
import kmitl.it13.millibear.eatallday.controller.activity.RandomRoomActivity;
import kmitl.it13.millibear.eatallday.model.Food;
import kmitl.it13.millibear.eatallday.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenRoomFragment extends Fragment {

    @BindView(R.id.btn_add)
    Button btn_add;

    private User mUser;
    private ArrayList<Food> mMenu;

    public KitchenRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUser = args.getParcelable("user");
        initialInstance();
    }

    private void initialInstance() {

        FoodApi.getFoodApi().getChildFood().orderByChild("userId").equalTo(mUser.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMenu = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mMenu.add(ds.getValue(Food.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitchen_room, container, false);
        ButterKnife.bind(this, rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    public static KitchenRoomFragment newInstance(User user) {

        Bundle args = new Bundle();
        KitchenRoomFragment fragment = new KitchenRoomFragment();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_add)
    public void onBtnAddTouched() {
        Intent intent = new Intent(getActivity(), AddFoodActivity.class);
        intent.putExtra("menu", mMenu);
        intent.putExtra("type", "food");
        intent.putExtra("userId", mUser.getUserId());
        startActivity(intent);
    }

    @OnClick(R.id.btn_random)
    public void onBtnRandomTouched() {
        Intent intent = new Intent(getActivity(), RandomRoomActivity.class);
        intent.putExtra("menu", mMenu);
        startActivity(intent);
    }
}
