package kmitl.it13.millibear.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import kmitl.it13.millibear.eatallday.model.Food;

public class FoodApi implements ValueEventListener{

    public interface FoodApiListener {

        public void onCreateObjectFoodApi(long id);
    }

    private static FoodApi foodApi;
    private DatabaseReference childFood;
    private FoodApiListener listener;
    private Context context;

    public void setListener(FoodApiListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private FoodApi(){

        childFood = FirebaseDatabase.getInstance().getReference("food");

        childFood.limitToLast(1).addListenerForSingleValueEvent(this);
    }

    public static FoodApi getFoodApi() {
        if(foodApi == null){
            foodApi = new FoodApi();
        }
        return foodApi;
    }

    public DatabaseReference getChildFood() {
        return childFood;
    }

    public void newFood(Context context, long id, Food newFood){

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/food/" + id, newFood);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        long id = 0;
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            id = Long.valueOf(ds.getKey());
        }

        if(id != 0){
            id += 1;
        }

        listener.onCreateObjectFoodApi(id);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
