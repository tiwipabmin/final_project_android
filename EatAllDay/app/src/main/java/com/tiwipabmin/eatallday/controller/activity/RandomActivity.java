package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiwipabmin.eatallday.model.Food;
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

    public static final int FOODITEM = 69;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_item1)
    ImageView iv_item1;

    @BindView(R.id.iv_item2)
    ImageView iv_item2;

    @BindView(R.id.iv_item3)
    ImageView iv_item3;

    @BindView(R.id.iv_item4)
    ImageView iv_item4;

    private ArrayList<Food> mMenu;
    private Intent intent;
    private ProgressDialogFragment progress;
    private Map<Integer, Food> chooseMenu;
    private int amount_random = 0;
    private boolean isTouched = true;
    private String defaultImage = "https://i.pinimg.com/originals/21/b8/ff/21b8ff6b2cc2731d72ccc4b7472fd915.jpg";

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

        this.intent = new Intent(this, ShowMenusActivity.class);

        progress = new ProgressDialogFragment();

        chooseMenu = new HashMap<>();
    }

    private void setUp() {

        intent.putExtra("menu", mMenu);
    }

    @OnClick(R.id.iv_item1)
    public void onIvItem1Touched() {
        intent.putExtra("item", "iv_item1");

        if(chooseMenu.get(0) == null) {
            amount_random++;
        }
        startActivityForResult(intent, FOODITEM);
    }

    @OnClick(R.id.iv_item2)
    public void onIvItem2Touched() {
        intent.putExtra("item", "iv_item2");
        if(chooseMenu.get(1) == null) {
            amount_random++;
        }
        startActivityForResult(intent, FOODITEM);
    }

    @OnClick(R.id.iv_item3)
    public void onIvItem3Touched() {
        intent.putExtra("item", "iv_item3");

        if(chooseMenu.get(2) == null) {
            amount_random++;
        }
        startActivityForResult(intent, FOODITEM);
    }

    @OnClick(R.id.iv_item4)
    public void onIvItem4Touched() {
        intent.putExtra("item", "iv_item4");

        if(chooseMenu.get(3) == null) {
            amount_random++;
        }
        startActivityForResult(intent, FOODITEM);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched() {
        finish();
    }

    @OnClick(R.id.btn_random_now)
    public void btnRandomNowTouched(){
        if(amount_random > 1 && isTouched) {
            isTouched = false;
            progress.show(getSupportFragmentManager(), "progress");

            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    int key = 0;
                    while(true) {
                        key = random.nextInt(4);
                        if(chooseMenu.get(key) != null){
                            break;
                        }
                    }
                    DialogFragment dialogFragment = new RandomResultDialogFragment().newInstance(RandomActivity.this, chooseMenu.get(key));
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(getSupportFragmentManager(), "random_result");
                    progress.dismiss();
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

            if (requestCode == FOODITEM) {

                int position = data.getIntExtra("position", -1);
                String item = data.getStringExtra("item");
                if (position != -1 && !mMenu.get(position).getImage().equals(defaultImage)) {
                    Food food = mMenu.get(position);
                    switch (item) {
                        case "iv_item1":
                            chooseMenu.put(0, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(food.getImage()).into(iv_item1);
                            break;
                        case "iv_item2":
                            chooseMenu.put(1, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(food.getImage()).into(iv_item2);
                            break;
                        case "iv_item3":
                            chooseMenu.put(2, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(food.getImage()).into(iv_item3);
                            break;
                        case "iv_item4":
                            chooseMenu.put(3, mMenu.get(position));
                            Glide.with(RandomActivity.this).load(food.getImage()).into(iv_item4);
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