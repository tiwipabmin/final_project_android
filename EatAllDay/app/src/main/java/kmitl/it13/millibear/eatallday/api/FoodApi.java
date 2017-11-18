package kmitl.it13.millibear.eatallday.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import kmitl.it13.millibear.eatallday.model.Food;

public class FoodApi {

    private static FoodApi foodApi;
    private DatabaseReference childFood;

    private FoodApi() {

        childFood = FirebaseDatabase.getInstance().getReference("food");
    }

    public static FoodApi getFoodApi() {
        if (foodApi == null) {
            foodApi = new FoodApi();
        }
        return foodApi;
    }

    public DatabaseReference getChildFood() {
        return childFood;
    }

    public void newFood(String newKey, Food newFood) {
        childFood.child(newKey).setValue(newFood);
    }

}
