package kmitl.it13.millibear.eatallday;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.model.Food;
import kmitl.it13.millibear.eatallday.model.User;

public class SaveImage {

    private Context mContext;
    private String mAlbumName = "EatAllDay", mNameFile = "EatAllDay_";

    public SaveImage(Context context) {
        this.mContext = context;
    }

    public String addImageToGallery(Bitmap bitmap) {

        File imageFile = createImageFile("EatAllDay");

        try {
//            String imagePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, imageName, "Capture");

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            galleryAddImage(Uri.fromFile(imageFile));

//            Toast.makeText(context, "Successed : " + imageFile, Toast.LENGTH_SHORT).show();

            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            Log.i("error", String.valueOf(e));
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

        return "error";
    }

    private File createImageFile(String albumName) {

        // make folder
        File mFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), albumName);
        if (!mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.i("error", "Directory not created : " + !mFolder.mkdirs());
            }
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageName = mNameFile + timeStamp;

        File imageFile = new File(mFolder, imageName + ".jpg");

        if (!imageFile.exists()) {
            try {
                if (!imageFile.createNewFile()) {
                    Log.i("error", "imageFile not create");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageFile;
    }

    private void galleryAddImage(Uri imageUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    public void saveImageToStorageFirebase(final Activity activity, final Uri uriImage, final DialogFragment progress, final Food menu) {
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("menuImages/"
                + menu.getId());
        ref.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progress.dismiss();

                if(uriImage != null){
                    menu.setImage(taskSnapshot.getDownloadUrl().toString());
                }

                FoodApi.getFoodApi().newFood(activity, menu.getId(), menu);
                activity.finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        Toast.makeText(activity, "Failed " + e.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void saveImageToStorageFirebase(final Activity activity, final Uri uriImage, final DialogFragment progress, final User user, final CircleImageView iv_Profile) {
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("menuImages/"
                + user.getUserId());
        ref.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progress.dismiss();

                if(uriImage != null){
                    user.setImage(taskSnapshot.getDownloadUrl().toString());
                    Glide.with(mContext).load(user.getImage()).into(iv_Profile);
                }

                UserApi.getUserApi().editProfileImage(mContext, user.getImage());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        Toast.makeText(activity, "Failed " + e.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
