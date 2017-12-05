package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiwipabmin.eatallday.model.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tiwipabmin.eatallday.R;

public class MenuDetailDialogFragment extends DialogFragment {

    @BindView(R.id.tv_cost)
    TextView tv_cost;

    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.iv_food)
    CircleImageView iv_food;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private Food mFood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mFood = args.getParcelable("food");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_detail_menu, null);
        ButterKnife.bind(this, view);

        String cost = String.valueOf(mFood.getCost()) + " " + mFood.getCurrency() + " / " + mFood.getAmount() + " " + mFood.getUnit();

        tv_title.setText(mFood.getName());
        tv_cost.setText(cost);
        tv_description.setText(mFood.getDescription());
        Glide.with(getActivity()).load(mFood.getImage()).into(iv_food);

        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.iv_cancel)
    public void onIvCancelTouched(){
        this.dismiss();
    }

    public static MenuDetailDialogFragment newInstance(Food food) {
        Bundle args = new Bundle();
        MenuDetailDialogFragment fragment = new MenuDetailDialogFragment();
        args.putParcelable("food", food);
        fragment.setArguments(args);
        return fragment;
    }
}
