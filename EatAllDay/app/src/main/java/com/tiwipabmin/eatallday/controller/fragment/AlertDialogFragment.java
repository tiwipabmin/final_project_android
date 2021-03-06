package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by tiwip on 10/29/2017.
 */

public class AlertDialogFragment extends DialogFragment {

    private String mContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        assert args != null;
        mContent = args.getString("mContent");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        alertDialog.setMessage(mContent)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return alertDialog.create();
    }

    public static AlertDialogFragment newInstance(String content) {

        Bundle args = new Bundle();
        AlertDialogFragment fragment = new AlertDialogFragment();
        args.putString("mContent", content);
        fragment.setArguments(args);
        return fragment;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
