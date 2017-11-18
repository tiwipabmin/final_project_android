package kmitl.it13.millibear.eatallday;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveImage {

    private Context mContext;
    private String mAlbumName = "EatAllDay", mNameFile = "EatAllDay_";

    public SaveImage(Context context) {
        this.mContext = context;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public String getNameFile() {
        return mNameFile;
    }

    public void setNameFile(String nameFile) {
        this.mNameFile = nameFile;
    }

    public String addImageToGallery(Bitmap bitmap){

        File imageFile = createImageFile("EatAllDay");

        try
        {
//            String imagePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, imageName, "Capture");

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            galleryAddImage(Uri.fromFile(imageFile));

//            Toast.makeText(context, "Successed : " + imageFile, Toast.LENGTH_SHORT).show();

            return imageFile.getAbsolutePath();
        }
        catch (Exception e)
        {
            Log.i("error", String.valueOf(e));
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }

        return "error";
    }

    private File createImageFile(String albumName) {

        // make folder
        File mFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), albumName);
        if(!mFolder.exists()){
            if(!mFolder.mkdirs()){
                Log.i("error", "Directory not created : " + !mFolder.mkdirs());
            }
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageName = mNameFile + timeStamp;

        File imageFile = new File(mFolder, imageName + ".jpg");

        if(!imageFile.exists()){
            try {
                if(!imageFile.createNewFile()){
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
}
