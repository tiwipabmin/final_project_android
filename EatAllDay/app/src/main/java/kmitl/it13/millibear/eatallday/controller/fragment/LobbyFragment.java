package kmitl.it13.millibear.eatallday.controller.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.LobbyAdapter;
import kmitl.it13.millibear.eatallday.model.History;
import kmitl.it13.millibear.eatallday.api.HistoryApi;
import kmitl.it13.millibear.eatallday.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyFragment extends Fragment {

    @BindView(R.id.profile)
    RecyclerView profile;

    private LobbyAdapter mLobbyAdapter;
    private User mUser;
    private DatabaseReference childHistory;

    public LobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUser = args.getParcelable("user");
        Toast.makeText(getActivity(), mUser.getUserId(), Toast.LENGTH_SHORT).show();
    }

    private void initialInstance(Context context){

        childHistory = HistoryApi.getHistoryApi().getChildHistory();

        mLobbyAdapter = new LobbyAdapter(context);
    }

    private void setUp(){

        childHistory.orderByChild("userId")
                .equalTo(mUser.getUserId())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<History> mStorage = new ArrayList<>();
                History deception = new History();
                mStorage.add(deception);

                ArrayList<String> mViewType = new ArrayList<>();
                mViewType.add("profile");

                //จะเก็บข้อมูลชัประวัติไว้ชั่วคราว
                ArrayList<History> memoryContent = new ArrayList<>();
                ArrayList<String> memoryType = new ArrayList<>();

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    History history = ds.getValue(History.class);

                    assert history != null;
                    history.setHisId(String.valueOf(ds.getKey()));

                    memoryContent.add(history);
                    memoryType.add(history.getType());
                }

                //เอาประวัติล่าสุดขึ้นมาไว้ลำดับแรก
                for(int i = memoryContent.size() - 1; i >= 0 ; i--){
                    mStorage.add(memoryContent.get(i));
                    mViewType.add(memoryType.get(i));
                }

                mLobbyAdapter.setStorage(mStorage);
                mLobbyAdapter.setViewType(mViewType);
                mLobbyAdapter.setUser(mUser);
                profile.setAdapter(mLobbyAdapter);
                profile.setLayoutManager(new LinearLayoutManager(getActivity()));
                profile.invalidateItemDecorations();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lobby, container, false);
        ButterKnife.bind(this, rootView);
        initialInstance(getContext());
        setUp();
        return rootView;
    }

    public static LobbyFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LobbyFragment fragment = new LobbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
