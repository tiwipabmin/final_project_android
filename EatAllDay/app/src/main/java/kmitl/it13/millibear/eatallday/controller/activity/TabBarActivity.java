package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.FriendApi;
import kmitl.it13.millibear.eatallday.controller.fragment.KitchenRoomFragment;
import kmitl.it13.millibear.eatallday.controller.fragment.SocialRoomFragment;
import kmitl.it13.millibear.eatallday.model.Friend;
import kmitl.it13.millibear.eatallday.model.User;
import kmitl.it13.millibear.eatallday.controller.fragment.LobbyRoomFragment;

public class TabBarActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private User mUser;
    private FriendApi mFriendApi;
    private DatabaseReference mChildFriend;
    private ArrayList<Friend> mFriendList;
    private NotificationBadge mBadgeFriend, mBadgeBell;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);
        ButterKnife.bind(TabBarActivity.this);

        Intent intent = getIntent();
        mUser = intent.getParcelableExtra("user");

        initialInstance();
        setUp();
    }

    private void initialInstance(){

        mFriendApi = FriendApi.getFriendApi();

        mChildFriend = mFriendApi.getChildFriend();

        mFriendList = new ArrayList<>();

        mBadgeFriend = findViewById(R.id.badge_friend);
        mBadgeBell = findViewById(R.id.badge_bell);
    }

    private void setUp(){

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        mChildFriend.orderByChild("receiver").equalTo(mUser.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Friend friend = ds.getValue(Friend.class);
                    mFriendList.add(friend);
                    assert friend != null;
                    if(!friend.isResponse()) {
                        count =+ 1;
                        Toast.makeText(TabBarActivity.this, "Count++ : " + count, Toast.LENGTH_SHORT).show();
                        mBadgeFriend.setNumber(count);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mChildFriend.orderByChild("receiver").equalTo(mUser.getUserId()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Friend friend = dataSnapshot.getValue(Friend.class);
//                mFriendList.add(friend);
//                assert friend != null;
//                if (!friend.isResponse()) {
//                    count += 1;
//                    mBadgeFriend.setNumber(count);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LobbyRoomFragment().newInstance(mUser), "LOBBY");
        adapter.addFragment(new KitchenRoomFragment().newInstance(mUser), "KITCHEN");
        adapter.addFragment(new SocialRoomFragment(), "SOCIAL");
        viewPager.setAdapter(adapter);
    }

    @OnClick(R.id.iv_logout)
    void onLogOutTouched(){
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
