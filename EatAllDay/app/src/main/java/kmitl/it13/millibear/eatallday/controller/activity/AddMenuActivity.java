package kmitl.it13.millibear.eatallday.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.Gallery;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SaveImage;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.controller.fragment.ProgressDialogFragment;
import kmitl.it13.millibear.eatallday.model.Food;

public class AddMenuActivity extends AppCompatActivity {

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

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.iv_menu)
    CircleImageView iv_menu;

    private String mUserId, mImage, mType;
    private FoodApi foodApi;
    private Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        Intent addFoodIntent = getIntent();
        mUserId = TabBarActivity.USER.getUserId();
        mType = addFoodIntent.getStringExtra("type");

        ButterKnife.bind(this);
        initialInstance();
    }

    private void initialInstance() {

        foodApi = FoodApi.getFoodApi();

        gallery = new Gallery();

    }

    @OnClick(R.id.iv_menu)
    public void onIvMenuTouched(){
        gallery.goToGallery(this, TabBarActivity.SELECT_IMAGE);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){
        finish();
    }

    @OnClick(R.id.iv_add)
    public void onIvAddTouched(){

        if(mType.equals("food") && !et_cost.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty() && mImage != null){

            String newKey = foodApi.getChildFood().push().getKey();
            Food newFood = new Food(newKey, et_name.getText().toString(), Long.valueOf(et_cost.getText().toString()), et_description.getText().toString(), mUserId, mImage, et_currency.getText().toString(),
                    Long.valueOf(et_amount.getText().toString()), et_unit.getText().toString());
            foodApi.newFood(this, newKey, newFood);
            this.finish();
        } else {

            Toast.makeText(this, "กรุณาใส่ข้อมูลให้ครบด้วยค่ะ!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == TabBarActivity.SELECT_IMAGE) {

                Uri uriSelectedImage = data.getData();
                String strPath = gallery.getRealPathFromURI(this, uriSelectedImage);

                File fImage = new File(strPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

                SaveImage saveImage = new SaveImage(this);
                mImage = saveImage.addImageToGallery(imageBitmap);

                iv_menu.setImageBitmap(imageBitmap);

            }

        }
    }
}
