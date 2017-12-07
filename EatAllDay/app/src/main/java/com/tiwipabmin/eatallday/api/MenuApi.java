package com.tiwipabmin.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiwipabmin.eatallday.model.Menu;

public class MenuApi {

    private static MenuApi menuApi;
    private DatabaseReference childMenu;

    private MenuApi() {

        childMenu = FirebaseDatabase.getInstance().getReference("menu");
    }

    public static MenuApi getMenuApi() {
        if (menuApi == null) {
            menuApi = new MenuApi();
        }
        return menuApi;
    }

    public DatabaseReference getChildMenu() {
        return childMenu;
    }

    public void newMenu(Context context, String newKey, Menu newMenu) {
        childMenu.child(newKey).setValue(newMenu);
        Toast.makeText(context, "เพิ่มรายการอาหารเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

    public void deleteMenu(Context context, Menu menu) {
        childMenu.child(menu.getId()).removeValue();
        Toast.makeText(context, "ลบรายการอาหารเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();

    }

    public void updateMenu(Context context, String menuId, Menu updateMenu) {
        childMenu.child(menuId).setValue(updateMenu);
        Toast.makeText(context, "แก้ไขประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();

    }

}
