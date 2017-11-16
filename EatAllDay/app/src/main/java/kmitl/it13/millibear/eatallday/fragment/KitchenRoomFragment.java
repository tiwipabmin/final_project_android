package kmitl.it13.millibear.eatallday.fragment;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.AddElementActivity;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.model.Food;
import kmitl.it13.millibear.eatallday.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenRoomFragment extends Fragment implements TypeElementDialogFragment.TypeElementDialogFragmentListener, FoodApi.FoodApiListener{

    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.iv_test)
    ImageView iv_test;

    private Intent addElementIntent;
    private User mUser;
    private ArrayList<Food> mMenu;

    public KitchenRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        FoodApi.getFoodApi().setListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUser = args.getParcelable("user");
        initialInstance();
    }

    private void initialInstance(){

        addElementIntent = new Intent(getActivity(), AddElementActivity.class);

        FoodApi.getFoodApi().getChildFood().orderByChild("userId").equalTo(mUser.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMenu = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    mMenu.add(ds.getValue(Food.class));
//                    Toast.makeText(KitchenRoomFragment.this.getActivity(), ds.getValue(Food.class) + "", Toast.LENGTH_SHORT).show();
                }
//                Glide.with(KitchenRoomFragment.this.getActivity()).load(food.getImage()).into(iv_test);
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
    public void onBtnAddTouched(){
        DialogFragment typeElementDialog = new TypeElementDialogFragment();
        ((TypeElementDialogFragment)typeElementDialog).setListener(this);
        typeElementDialog.show(getActivity().getSupportFragmentManager(), "typeElement");
    }

    @Override
    public void onTypeFoodTouched() {

        addElementIntent.putExtra("type", "food");
        addElementIntent.putExtra("userId", mUser.getUserId());
        startActivity(addElementIntent);
    }

    @Override
    public void onTypeRestaurantTouched() {
        addElementIntent.putExtra("type", "restaurant");
        addElementIntent.putExtra("userId", mUser.getUserId());
        startActivity(addElementIntent);
    }

    @Override
    public void onCreateObjectFoodApi(long id) {

    }
}
