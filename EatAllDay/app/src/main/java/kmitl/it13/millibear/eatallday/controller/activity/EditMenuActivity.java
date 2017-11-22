package kmitl.it13.millibear.eatallday.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.Gallery;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SaveImage;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.model.Food;

public class EditMenuActivity extends AppCompatActivity {

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

    @BindView(R.id.iv_verify)
    ImageView iv_verify;

    @BindView(R.id.iv_menu)
    CircleImageView iv_menu;

    private Food mMenu;
    private String mImage;
    private Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        ButterKnife.bind(this);

        Intent editMenuIntent = getIntent();
        mMenu = editMenuIntent.getParcelableExtra("menu");

        initialInstance();
        setUp();
    }

    private void initialInstance(){

        gallery = new Gallery();
    }

    private void setUp(){

        Glide.with(this).load(mMenu.getImage()).into(iv_menu);
        et_name.setText(mMenu.getName());
        et_cost.setText(String.valueOf(mMenu.getCost()));
        et_currency.setText(mMenu.getCurrency());
        et_amount.setText(String.valueOf(mMenu.getAmount()));
        et_unit.setText(mMenu.getUnit());
        et_description.setText(mMenu.getDescription());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched(){

        finish();
    }

    @OnClick(R.id.iv_menu)
    public void onIvMenuTouched(){

        gallery.goToGallery(this, TabBarActivity.SELECT_FILE);
    }

    @OnClick(R.id.iv_verify)
    public void onIvVerifyTouched(){

        if(!et_cost.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty()){

            Toast.makeText(this, "เพิ่มรายการอาหารเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();

            String menuId = mMenu.getId();
            Food newFood = new Food(menuId, et_name.getText().toString(), Long.valueOf(et_cost.getText().toString()), et_description.getText().toString(), TabBarActivity.USER_ID, mImage, et_currency.getText().toString(),
                    Long.valueOf(et_amount.getText().toString()), et_unit.getText().toString());
            FoodApi.getFoodApi().newFood(menuId, newFood);
            finish();
        } else {

            Toast.makeText(this, "กรุณาใส่ข้อมูลให้ครบด้วยค่ะ!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == TabBarActivity.SELECT_FILE) {

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
