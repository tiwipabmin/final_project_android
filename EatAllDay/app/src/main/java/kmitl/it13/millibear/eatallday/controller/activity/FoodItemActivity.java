package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.MenuAdapter;
import kmitl.it13.millibear.eatallday.model.Food;

public class FoodItemActivity extends AppCompatActivity implements MenuAdapter.MenuListener{

    @BindView(R.id.rv_foodItem)
    RecyclerView rv_foodItem;

    @BindView(R.id.iv_logout)
    ImageView iv_logout;

    @BindView(R.id.iv_user)
    ImageView iv_user;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    private NotificationBadge mBadgeFriend;

    private ArrayList<Food> mMenu;
    private MenuAdapter mMenuAdapter;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
        ButterKnife.bind(FoodItemActivity.this);

        Intent foodItemIntent = getIntent();

        initialInstance(foodItemIntent);
        setUp();
    }

    private void initialInstance(Intent intent){

        bindWidget();

        mMenu = intent.getParcelableArrayListExtra("menu");

        mMenuAdapter = new MenuAdapter(FoodItemActivity.this, mMenu, FoodItemActivity.this, 1);

        item = intent.getStringExtra("item");
    }

    private void bindWidget(){

        mBadgeFriend = findViewById(R.id.badge_friend);
    }

    private void setUp(){

        rv_foodItem.setAdapter(mMenuAdapter);
        rv_foodItem.setLayoutManager(new LinearLayoutManager(FoodItemActivity.this));

        iv_logout.setVisibility(View.GONE);
        iv_user.setVisibility(View.GONE);
        iv_back.setVisibility(View.VISIBLE);
        mBadgeFriend.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){
        finish();
    }

    @Override
    public void onBtnAddTouched(int position) {
        Intent intent = new Intent(FoodItemActivity.this, RandomRoomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("item", item);
        setResult(RESULT_OK, intent);
        finish();
    }
}
