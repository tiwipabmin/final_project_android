package kmitl.it13.millibear.eatallday.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.model.Food;

public class AddFoodActivity extends AppCompatActivity {

    final private int SELECT_FILE = 1969;

    @BindView(R.id.iv_food)
    ImageView iv_food;

    @BindView(R.id.et_cost)
    EditText et_cost;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_currency)
    EditText et_currency;

    @BindView(R.id.et_amount)
    EditText et_amount;

    @BindView(R.id.et_unit)
    EditText et_unit;

    @BindView(R.id.et_description)
    EditText et_description;

    @BindView(R.id.iv_logout)
    ImageView iv_logout;

    @BindView(R.id.iv_user)
    ImageView iv_user;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    private NotificationBadge mBadgeFriend;
    private String mUserId, mImage, mType;
    private FoodApi foodApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Intent addFoodIntent = getIntent();
        mUserId = addFoodIntent.getStringExtra("userId");
        mType = addFoodIntent.getStringExtra("type");

        ButterKnife.bind(this);
        initialInstance();
        setUp();
    }

    private void initialInstance() {

        foodApi = FoodApi.getFoodApi();

        bindWidget();
    }

    private void bindWidget(){

        mBadgeFriend = findViewById(R.id.badge_friend);
    }

    private void setUp(){

        iv_logout.setVisibility(View.GONE);
        iv_user.setVisibility(View.GONE);
        iv_back.setVisibility(View.VISIBLE);
        iv_add.setVisibility(View.VISIBLE);
        mBadgeFriend.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_food)
    public void onImageViewElementTouched() {
        goToGallery();
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){
        finish();
    }

    @OnClick(R.id.iv_add)
    public void onIvAddTouched(){
        if(mType.equals("food") && !et_cost.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty() && mImage != null){

            Toast.makeText(this, "Add New Food", Toast.LENGTH_SHORT).show();

            String newKey = foodApi.getChildFood().push().getKey();
            Food newFood = new Food(newKey, et_name.getText().toString(), Long.valueOf(et_cost.getText().toString()), et_description.getText().toString(), mUserId, mImage, et_currency.getText().toString(),
                    Long.valueOf(et_amount.getText().toString()), et_unit.getText().toString());
            foodApi.newFood(newKey, newFood);
            finish();
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
        this.getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE) {

                Uri uriSelectedImage = data.getData();
                String strPath = getRealPathFromURI(this, uriSelectedImage);

                File fImage = new File(strPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

                mImage = addImageToGallery(imageBitmap);

                iv_food.setImageBitmap(imageBitmap);

            }

        }
    }
}
