package com.tiwipabmin.eatallday.controller.fragment;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tiwipabmin.eatallday.api.MenuApi;
import com.tiwipabmin.eatallday.controller.activity.AddMenuActivity;
import com.tiwipabmin.eatallday.controller.activity.RandomActivity;
import com.tiwipabmin.eatallday.controller.activity.TabBarActivity;
import com.tiwipabmin.eatallday.model.Menu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.adapter.MenusAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitchenFragment extends Fragment {

    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.rv_menu)
    RecyclerView rv_menu;

    private ArrayList<Menu> mMenus;
    private MenusAdapter mMenuAdapter;

    public KitchenFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initialInstance() {

        MenuApi.getMenuApi().getChildMenu().orderByChild("userId").equalTo(TabBarActivity.USER.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMenus = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mMenus.add(ds.getValue(Menu.class));
                }
                mMenuAdapter = new MenusAdapter(getContext(), mMenus, 0);
                rv_menu.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_menu.setAdapter(mMenuAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);
        ButterKnife.bind(this, rootView);
        initialInstance();
        // Inflate the layout for this fragment
        return rootView;
    }

    @OnClick(R.id.btn_add)
    public void onBtnAddTouched() {
        Intent intent = new Intent(getActivity(), AddMenuActivity.class);
        intent.putExtra("menu", mMenus);
        intent.putExtra("type", "menu");
        startActivity(intent);
    }

    @OnClick(R.id.btn_random)
    public void onBtnRandomTouched() {
        Intent intent = new Intent(getActivity(), RandomActivity.class);
        intent.putExtra("menu", mMenus);
        startActivity(intent);
    }
}
