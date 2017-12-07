package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tiwipabmin.eatallday.Gallery;
import com.tiwipabmin.eatallday.SaveImage;
import com.tiwipabmin.eatallday.api.UserApi;
import com.tiwipabmin.eatallday.controller.activity.TabBarActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tiwipabmin.eatallday.R;

public class EditProfileDisplayDialogFragment extends DialogFragment {

    private final int SELECT_PROFILE_IMAGE = 1234;

    private CircleImageView mProfileImage;
    private Gallery mGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mGallery = new Gallery();
    }


    public void setProfileImage(CircleImageView profileImage) {
        this.mProfileImage = profileImage;
    }

    @OnClick(R.id.item_edit_profile_display)
    public void onItemEditProfileDisplayTouched() {

        mGallery.goToGallery(this, SELECT_PROFILE_IMAGE);
    }

    @OnClick(R.id.item_delete_profile_display)
    public void onItemDeleteProfileDisplayTouched() {

        UserApi.getUserApi()
                .deleteProfileImage();
        dismiss();
        Toast.makeText(getContext(), "ลบรูปภาพประจำตัวเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_profile_display, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        return alertDialog.create();
    }

    public static EditProfileDisplayDialogFragment newInstance(CircleImageView profileImage) {
        Bundle args = new Bundle();
        EditProfileDisplayDialogFragment fragment = new EditProfileDisplayDialogFragment();
        fragment.setProfileImage(profileImage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == FragmentActivity.RESULT_OK) {

            if (requestCode == SELECT_PROFILE_IMAGE) {

                Uri uriSelectedImage = data.getData();

                DialogFragment progress = new ProgressDialogFragment();
                progress.show(getActivity().getSupportFragmentManager(), "progress");

                SaveImage saveImage = new SaveImage(getContext());
                saveImage.saveImageToStorageFirebase(getActivity(), uriSelectedImage, progress, TabBarActivity.USER, mProfileImage);

                EditProfileDisplayDialogFragment.this.dismiss();
            }
        }

    }
}
