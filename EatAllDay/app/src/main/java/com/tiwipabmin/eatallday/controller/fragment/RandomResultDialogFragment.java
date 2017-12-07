package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiwipabmin.eatallday.model.Menu;

import com.tiwipabmin.eatallday.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RandomResultDialogFragment extends DialogFragment {

    @BindView(R.id.iv_menu)
    ImageView mIv_menu;

    @BindView(R.id.tv_menuName)
    TextView mTv_menuName;

    private Menu mMenu;
    private Context mContext;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mMenu = args.getParcelable("menu");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_random_result, null);

        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(mMenu.getImage()).into(mIv_menu);
        mTv_menuName.setText(mMenu.getName());
        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.btn_accept)
    public void onButtonAcceptTouched(){
        DialogFragment topicsDialog = new AddTopicDialogFragment().newInstance(mContext, mMenu);
        topicsDialog.setCancelable(false);
        topicsDialog.show(getActivity().getSupportFragmentManager(), "topicsDialog");
        dismiss();
    }

    public static RandomResultDialogFragment newInstance(Context context, Menu menu) {
        Bundle args = new Bundle();
        args.putParcelable("menu", menu);
        RandomResultDialogFragment fragment = new RandomResultDialogFragment();
        fragment.setContext(context);
        fragment.setArguments(args);
        return fragment;
    }
}
