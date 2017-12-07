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
import com.tiwipabmin.eatallday.model.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tiwipabmin.eatallday.R;

public class MenuDetailDialogFragment extends DialogFragment {

    @BindView(R.id.tv_cost)
    TextView mTv_cost;

    @BindView(R.id.tv_description)
    TextView mTv_description;

    @BindView(R.id.iv_menu)
    CircleImageView mIv_menu;

    @BindView(R.id.tv_title)
    TextView mTv_title;

    private Menu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mMenu = args.getParcelable("menu");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_detail_menu, null);
        ButterKnife.bind(this, view);

        String cost = String.valueOf(mMenu.getCost()) + " " + mMenu.getCurrency() + " / " + mMenu.getAmount() + " " + mMenu.getUnit();

        mTv_title.setText(mMenu.getName());
        mTv_cost.setText(cost);
        mTv_description.setText(mMenu.getDescription());
        Glide.with(getActivity()).load(mMenu.getImage()).into(mIv_menu);

        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.iv_cancel)
    public void onImageViewCancelTouched(){
        this.dismiss();
    }

    public static MenuDetailDialogFragment newInstance(Menu menu) {
        Bundle args = new Bundle();
        MenuDetailDialogFragment fragment = new MenuDetailDialogFragment();
        args.putParcelable("menu", menu);
        fragment.setArguments(args);
        return fragment;
    }
}
