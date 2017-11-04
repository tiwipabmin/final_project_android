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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyRoomFragment extends Fragment {

//    @BindView(R.id.profile)
//    RecyclerView profile;

    private List<String> viewType;
    private List<Object> data;
    private PostAdapter postAdapter;
    private RecyclerView profile;

    public LobbyRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialInstance(Context context){

        viewType = new ArrayList<>();
        viewType.add("profile");
        viewType.add("profile");
        viewType.add("profile");
        postAdapter = new PostAdapter(context, viewType);
        profile.setAdapter(postAdapter);
        profile.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lobby_room, container, false);
        profile = rootView.findViewById(R.id.profile);
        initialInstance(getContext());
        return rootView;
    }

}
