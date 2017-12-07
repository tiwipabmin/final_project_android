package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.tiwipabmin.eatallday.model.Menu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;
import com.tiwipabmin.eatallday.adapter.MenusAdapter;

public class ShowMenusActivity extends AppCompatActivity implements MenusAdapter.MenusListener{

    @BindView(R.id.rv_foodItem)
    RecyclerView mRv_foodItem;

    @BindView(R.id.iv_back)
    ImageView mIv_back;

    private ArrayList<Menu> mMenu;
    private MenusAdapter mMenuAdapter;
    private String mItem;

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

        mItem = intent.getStringExtra("cv_item");
    }

    private void setUp(){

        mRv_foodItem.setAdapter(mMenuAdapter);
        mRv_foodItem.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){
        finish();
    }

    @Override
    public void onBtnAddTouched(int position) {
        Intent intent = new Intent(this, RandomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("cv_item", mItem);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}

