package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.tiwipabmin.eatallday.EatAllDayPreferences;
import com.tiwipabmin.eatallday.controller.activity.SignInActivity;
import com.tiwipabmin.eatallday.controller.activity.TabBarActivity;

public class VerificationLogoutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("คุณแน่ใจใช่ไหมที่จะออกจากระบบ?")
                .setPositiveButton("ใช่.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        EatAllDayPreferences
                                .clearEatAllDayPreferences(VerificationLogoutDialogFragment
                                .this
                                .getContext());

                        Intent intent = new Intent(VerificationLogoutDialogFragment
                                .this.getActivity(),
                                SignInActivity.class);
                        startActivity(intent);
                        ((TabBarActivity) VerificationLogoutDialogFragment
                                .this
                                .getActivity())
                                .finish();
                        Toast.makeText(VerificationLogoutDialogFragment
                                        .this.getContext(),
                                "ออกจากระบบเรียบร้อย.",
                                Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).setNegativeButton("ไม่ใช่.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return alertDialog.create();
    }
}
