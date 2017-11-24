package kmitl.it13.millibear.eatallday.api;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import kmitl.it13.millibear.eatallday.controller.fragment.ProgressDialogFragment;
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

    public void newFood(String newKey, Food newFood, final DialogFragment progress) {
        childFood.child(newKey).setValue(newFood);
        Toast.makeText(progress.getContext(), "เพิ่มรายการอาหารเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
        progress.dismiss();
    }

    public void deleteMenu(Food food, final DialogFragment progress) {
        childFood.child(food.getId()).removeValue();
        Toast.makeText(progress.getContext(), "ลบรายการอาหารเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();
        progress.dismiss();

    }

    public void updateMenu(String menuId, Food updateMenu, final DialogFragment progress) {
        childFood.child(menuId).setValue(updateMenu);
        Toast.makeText(progress.getContext(), "แก้ไขประวิตเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
        progress.dismiss();

    }

}
