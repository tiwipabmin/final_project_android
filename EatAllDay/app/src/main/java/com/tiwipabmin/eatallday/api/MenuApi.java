package com.tiwipabmin.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiwipabmin.eatallday.model.Food;

public class MenuApi {

    private static MenuApi menuApi;
    private DatabaseReference childFood;

    private MenuApi() {


        childFood = FirebaseDatabase.getInstance().getReference("food");
    }

    public static MenuApi getMenuApi() {
        if (menuApi == null) {
            menuApi = new MenuApi();
        }
        return menuApi;
    }

    public DatabaseReference getChildFood() {
        return childFood;
    }

    public void newFood(Context context, String newKey, Food newFood) {
        childFood.child(newKey).setValue(newFood);
        Toast.makeText(context, "เพิ่มรายการอาหารเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

    public void deleteMenu(Context context, Food food) {
        childFood.child(food.getId()).removeValue();
        Toast.makeText(context, "ลบรายการอาหารเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();

    }

    public void updateMenu(Context context, String menuId, Food updateMenu) {
        childFood.child(menuId).setValue(updateMenu);
        Toast.makeText(context, "แก้ไขประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();

    }

}
