package kmitl.it13.millibear.eatallday;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.api.FoodApi.FoodApiListener;
import kmitl.it13.millibear.eatallday.model.Food;

public class AddElementActivity extends AppCompatActivity implements FoodApi.FoodApiListener {

    final int REQUEST_WRITE_EXTERNAL_PERMISSIONS = 1234;
    final int REQUEST_READ_EXTERNAL_PERMISSIONS = 1235;

    final private int SELECT_FILE = 1969;

    @BindView(R.id.iv_element)
    ImageView iv_element;

    private String mDescription = "None", mName, mCost, mUserId, mType, mImage;
    private FoodApi foodApi;
    private long id;

    @Override
    protected void onResume() {
        super.onResume();
        FoodApi.getFoodApi().setListener(this);
        FoodApi.getFoodApi().setContext(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element);
        Intent addElementIntent = getIntent();
        mType = addElementIntent.getStringExtra("type");
        mUserId = addElementIntent.getStringExtra("userId");

        ButterKnife.bind(AddElementActivity.this);
        checkPermission();
        initialInstance();
    }

    private void initialInstance() {

        foodApi = FoodApi.getFoodApi();

    }

    @OnClick(R.id.iv_element)
    public void onImageViewElementTouched() {
        goToGallery();
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnCancelTouched(){
        finish();
    }

    @OnClick(R.id.btn_add)
    public void onBtnAddTouched(){
        if(!mCost.isEmpty() && !mName.isEmpty() && mImage != null && mType.equals("food")){
            Toast.makeText(AddElementActivity.this, "Add New Food", Toast.LENGTH_SHORT).show();
            Food newFood = new Food(id, mName, Long.valueOf(mCost), mDescription, mUserId, mImage);
            foodApi.newFood(AddElementActivity.this, id, newFood);
            finish();
        }
        Toast.makeText(AddElementActivity.this, "Error! Add New Food" + ", image : " + id, Toast.LENGTH_SHORT).show();
    }

    @OnTextChanged(value = R.id.et_name,
    callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterNameInput(Editable editable) {

        mName = editable.toString();
    }

    @OnTextChanged(value = R.id.et_cost,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterCostInput(Editable editable) {

        mCost = editable.toString();
    }

    @OnTextChanged(value = R.id.et_description,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterDescriptionInput(Editable editable) {

        mDescription = editable.toString();
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(AddElementActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddElementActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_PERMISSIONS);
            Toast.makeText(this, "Permission grated", Toast.LENGTH_SHORT).show();
        } else if (ContextCompat.checkSelfPermission(AddElementActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddElementActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_PERMISSIONS);
            Toast.makeText(this, "Permission grated", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
        String imageName = "EatAllDay_" + timeStamp;

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
        AddElementActivity.this.getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE) {

                Uri uriSelectedImage = data.getData();
                String strPath = getRealPathFromURI(AddElementActivity.this, uriSelectedImage);

                File fImage = new File(strPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

                mImage = addImageToGallery(imageBitmap);

                iv_element.setImageBitmap(imageBitmap);

            }

        }
    }

    @Override
    public void onCreateObjectFoodApi(long id) {
        this.id = id;
    }
}
