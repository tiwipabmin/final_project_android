package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiwipabmin.eatallday.model.Menu;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.controller.fragment.RandomResultDialogFragment;

public class RandomActivity extends AppCompatActivity {

    public static final int ITEM_MENU = 69;

    @BindView(R.id.iv_back)
    ImageView mIv_back;

    @BindView(R.id.iv_item1)
    ImageView mIv_item1;

    @BindView(R.id.iv_item2)
    ImageView mIv_item2;

    @BindView(R.id.iv_item3)
    ImageView mIv_item3;

    @BindView(R.id.iv_item4)
    ImageView mIv_item4;

    private ArrayList<Menu> mMenu;
    private Intent mIntent;
    private ProgressDialogFragment mProgress;
    private Map<Integer, Menu> mChooseMenu;
    private int mAmountRandom = 0;
    private boolean mIsTouched = true;
    private String mDefaultImage = "https://i.pinimg.com/originals/21/b8/ff/21b8ff6b2cc2731d72ccc4b7472fd915.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        ButterKnife.bind(RandomActivity.this);

        Intent randomRoomIntent = getIntent();

        initialInstance(randomRoomIntent);
        setUp();
    }

    private void initialInstance(Intent intent) {

        mMenu = intent.getParcelableArrayListExtra("menu");

        this.mIntent = new Intent(this, ShowMenusActivity.class);

        mProgress = new ProgressDialogFragment();

        mChooseMenu = new HashMap<>();
    }

    private void setUp() {

        mIntent.putExtra("menu", mMenu);
    }

    @OnClick(R.id.iv_item1)
    public void onIvItem1Touched() {
        mIntent.putExtra("cv_item", "mIv_item1");

        if(mChooseMenu.get(0) == null) {
            mAmountRandom++;
        }
        startActivityForResult(mIntent, ITEM_MENU);
    }

    @OnClick(R.id.iv_item2)
    public void onIvItem2Touched() {
        mIntent.putExtra("cv_item", "mIv_item2");
        if(mChooseMenu.get(1) == null) {
            mAmountRandom++;
        }
        startActivityForResult(mIntent, ITEM_MENU);
    }

    @OnClick(R.id.iv_item3)
    public void onIvItem3Touched() {
        mIntent.putExtra("cv_item", "mIv_item3");

        if(mChooseMenu.get(2) == null) {
            mAmountRandom++;
        }
        startActivityForResult(mIntent, ITEM_MENU);
    }

    @OnClick(R.id.iv_item4)
    public void onIvItem4Touched() {
        mIntent.putExtra("cv_item", "mIv_item4");

        if(mChooseMenu.get(3) == null) {
            mAmountRandom++;
        }
        startActivityForResult(mIntent, ITEM_MENU);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched() {
        finish();
    }

    @OnClick(R.id.btn_random_now)
    public void btnRandomNowTouched(){
        if(mAmountRandom > 1 && mIsTouched) {
            mIsTouched = false;
            mProgress.show(getSupportFragmentManager(), "mProgress");

            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    int key = 0;
                    while(true) {
                        key = random.nextInt(4);
                        if(mChooseMenu.get(key) != null){
                            break;
                        }
                    }
                    DialogFragment dialogFragment = new RandomResultDialogFragment().newInstance(RandomActivity.this, mChooseMenu.get(key));
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(getSupportFragmentManager(), "random_result");
                    mProgress.dismiss();
                }
            };

            handler.postDelayed(runnable, 3000);
        } else {

            Toast.makeText(this, "จำนวนในการสุ่มน้อยเกินไป.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == ITEM_MENU) {

                int position = data.getIntExtra("position", -1);
                String item = data.getStringExtra("cv_item");
                if (position != -1 && !mMenu.get(position).getImage().equals(mDefaultImage)) {
                    Menu menu = mMenu.get(position);
                    switch (item) {
                        case "mIv_item1":
                            mChooseMenu.put(0, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(menu.getImage()).into(mIv_item1);
                            break;
                        case "mIv_item2":
                            mChooseMenu.put(1, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(menu.getImage()).into(mIv_item2);
                            break;
                        case "mIv_item3":
                            mChooseMenu.put(2, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(menu.getImage()).into(mIv_item3);
                            break;
                        case "mIv_item4":
                            mChooseMenu.put(3, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(menu.getImage()).into(mIv_item4);
                            break;
                    }
                } else {

                    Toast.makeText(this, "ไม่สามารถเลือกได้ กรุณาเปลี่ยนรูปภาพก่อน.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
