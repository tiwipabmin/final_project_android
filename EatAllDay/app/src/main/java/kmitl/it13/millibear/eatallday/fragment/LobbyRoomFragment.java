package kmitl.it13.millibear.eatallday.fragment;


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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SignInActivity;
import kmitl.it13.millibear.eatallday.adapter.PostAdapter;
import kmitl.it13.millibear.eatallday.model.History;
import kmitl.it13.millibear.eatallday.api.HistoryApi;
import kmitl.it13.millibear.eatallday.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyRoomFragment extends Fragment {

    @BindView(R.id.profile)
    RecyclerView profile;

    private List<String> mViewType;
    private List<History> mStorage;
    private PostAdapter mPostAdapter;
    private User mUser;
    private DatabaseReference childHistory;

    public LobbyRoomFragment() {
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

        mStorage = new ArrayList<>();
        History deception = new History();
        mStorage.add(deception);

        mViewType = new ArrayList<>();
        mViewType.add("profile");
        mPostAdapter = new PostAdapter(context);
    }

    private void setUp(){

        childHistory.orderByChild("userId")
                .equalTo(mUser.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    History history = ds.getValue(History.class);

                    assert history != null;
                    history.setHisId(Long.valueOf(ds.getKey()));

                    mStorage.add(history);
                    mViewType.add(history.getType());

                }
                mPostAdapter.setStorage(mStorage);
                mPostAdapter.setViewType(mViewType);
                mPostAdapter.setUser(mUser);
                profile.setAdapter(mPostAdapter);
                profile.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        View rootView = inflater.inflate(R.layout.fragment_lobby_room, container, false);
        ButterKnife.bind(this, rootView);
        initialInstance(getContext());
        setUp();
        return rootView;
    }

    public static LobbyRoomFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LobbyRoomFragment fragment = new LobbyRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
