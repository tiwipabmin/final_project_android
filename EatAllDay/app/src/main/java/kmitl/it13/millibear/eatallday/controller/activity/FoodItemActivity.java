package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.FoodItemAdapter;
import kmitl.it13.millibear.eatallday.model.Food;

public class FoodItemActivity extends AppCompatActivity {

    @BindView(R.id.rv_foodItem)
    RecyclerView rv_foodItem;

    private ArrayList<Food> mMenu;
    private FoodItemAdapter foodItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
        ButterKnife.bind(this);

        Intent foodItemIntent = getIntent();

        initialInstance(foodItemIntent);
        setUp();
    }

    private void initialInstance(Intent intent){

        mMenu = intent.getParcelableArrayListExtra("menu");

        foodItemAdapter = new FoodItemAdapter(FoodItemActivity.this, mMenu);
    }

    private void setUp(){

        rv_foodItem.setAdapter(foodItemAdapter);
        rv_foodItem.setLayoutManager(new LinearLayoutManager(FoodItemActivity.this));
    }
}
