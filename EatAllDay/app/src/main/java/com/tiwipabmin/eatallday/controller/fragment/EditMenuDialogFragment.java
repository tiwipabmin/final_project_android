package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.tiwipabmin.eatallday.api.MenuApi;
import com.tiwipabmin.eatallday.controller.activity.EditMenuActivity;
import com.tiwipabmin.eatallday.model.Food;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

public class EditMenuDialogFragment extends DialogFragment {

    private Food mMenu;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mMenu = args.getParcelable("food");
    }

    @OnClick(R.id.item_edit_menu)
    public void onItemEditMenuTouched(){

        Intent intent = new Intent(getActivity(), EditMenuActivity.class);
        intent.putExtra("menu", mMenu);
        startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.item_delete_menu)
    public void onItemDeleteMenuTouched(){

        MenuApi.getMenuApi().deleteMenu(getActivity(), mMenu);
        this.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_menu, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        return alertDialog.create();
    }

    public static EditMenuDialogFragment newInstance(Food food) {

        Bundle args = new Bundle();
        args.putParcelable("food", food);
        EditMenuDialogFragment fragment = new EditMenuDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this, view).unbind();
    }
}
