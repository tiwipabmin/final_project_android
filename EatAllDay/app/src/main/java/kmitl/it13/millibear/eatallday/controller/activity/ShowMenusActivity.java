package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.MenusAdapter;
import kmitl.it13.millibear.eatallday.model.Food;

public class ShowMenusActivity extends AppCompatActivity implements MenusAdapter.MenusListener{

    @BindView(R.id.rv_foodItem)
    RecyclerView rv_foodItem;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    private ArrayList<Food> mMenu;
    private MenusAdapter mMenuAdapter;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menus);
        ButterKnife.bind(this);

        Intent foodItemIntent = getIntent();

        initialInstance(foodItemIntent);
        setUp();
    }

    private void initialInstance(Intent intent){

        mMenu = intent.getParcelableArrayListExtra("menu");

        mMenuAdapter = new MenusAdapter(this, mMenu, this, 1);

        item = intent.getStringExtra("item");
    }

    private void setUp(){

        rv_foodItem.setAdapter(mMenuAdapter);
        rv_foodItem.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){
        finish();
    }

    @Override
    public void onBtnAddTouched(int position) {
        Intent intent = new Intent(this, RandomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("item", item);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}

