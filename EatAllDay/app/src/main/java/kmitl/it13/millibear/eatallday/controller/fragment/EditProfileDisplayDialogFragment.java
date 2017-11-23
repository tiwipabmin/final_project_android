package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.Gallery;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SaveImage;
import kmitl.it13.millibear.eatallday.model.User;

public class EditProfileDisplayDialogFragment extends DialogFragment {

    private final int SELECT_PROFILE_IMAGE = 1234;

    private User mUser;
    private CircleImageView mProfileImage;
    private Gallery mGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUser = args.getParcelable("user");
        mGallery = new Gallery();
    }


    public void setProfileImage(CircleImageView profileImage) {
        this.mProfileImage = profileImage;
    }

    @OnClick(R.id.item_edit_profile_display)
    public void onItemEditProfileDisplayTouched() {

//        Glide.with(getContext()).load("https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/User-info.svg/1024px-User-info.svg.png").into(mProfileImage);
        mGallery.goToGallery(getActivity(), SELECT_PROFILE_IMAGE);
    }

    @OnClick(R.id.item_delete_profile_display)
    public void onItemDeleteProfileDisplayTouched() {

        deleteProfileImage("https://upload.wikimedia.org/wikipedia/en/thumb/c/ce/User-info.svg/1024px-User-info.svg.png");
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

    public static EditProfileDisplayDialogFragment newInstance(User user, CircleImageView profileImage) {

        Bundle args = new Bundle();
        EditProfileDisplayDialogFragment fragment = new EditProfileDisplayDialogFragment();
        fragment.setProfileImage(profileImage);
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteProfileImage(String profileImage) {
//        UserApi.getUserApi().getChildUser().
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PROFILE_IMAGE) {

            Uri uriSelectedImage = data.getData();
            String strPath = mGallery.getRealPathFromURI(getContext(), uriSelectedImage);

            File fImage = new File(strPath);
            Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

            SaveImage saveImage = new SaveImage(getContext());
//            String image = saveImage.addImageToGallery(imageBitmap);

            Toast.makeText(getContext(), "mProfile", Toast.LENGTH_SHORT).show();
            mProfileImage.setImageBitmap(imageBitmap);

            EditProfileDisplayDialogFragment.this.dismiss();
        }

    }
}
