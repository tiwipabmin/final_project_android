package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.model.Food;

public class RandomRoomActivity extends AppCompatActivity {

    private ArrayList<Food> mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_room);
        ButterKnife.bind(this);

        Intent randomRoomIntent = getIntent();

        initialInstance(randomRoomIntent);
    }

    private void initialInstance(Intent intent){

        mMenu = intent.getParcelableArrayListExtra("menu");
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnCancelTouched(){
        finish();
    }

    @OnClick(R.id.iv_item1)
    public void onIvItem1Touched(){
        Intent intent = new Intent(RandomRoomActivity.this, FoodItemActivity.class);
        intent.putExtra("menu", mMenu);
        startActivity(intent);
    }
}
