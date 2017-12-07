package com.tiwipabmin.eatallday.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tiwipabmin.eatallday.Gallery;
import com.tiwipabmin.eatallday.api.MenuApi;
import com.tiwipabmin.eatallday.model.Menu;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.SaveImage;

public class AddMenuActivity extends AppCompatActivity {

    @BindView(R.id.et_cost)
    EditText mEt_cost;

    @BindView(R.id.et_name)
    EditText mEt_name;

    @BindView(R.id.et_currency)
    EditText mEt_currency;

    @BindView(R.id.et_amount)
    EditText mEt_amount;

    @BindView(R.id.et_unit)
    EditText mEt_unit;

    @BindView(R.id.et_description)
    EditText mEt_description;

    @BindView(R.id.iv_back)
    ImageView mIv_back;

    @BindView(R.id.iv_add)
    ImageView mIv_add;

    @BindView(R.id.iv_menu)
    CircleImageView mIv_menu;

    private String mType;
    private MenuApi mMenuApi;
    private Gallery mGallery;
    private Uri mUriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        Intent addFoodIntent = getIntent();
        mType = addFoodIntent.getStringExtra("type");

        ButterKnife.bind(this);
        initialInstance();
    }

    private void initialInstance() {

        mMenuApi = MenuApi.getMenuApi();

        mGallery = new Gallery();

    }

    @OnClick(R.id.iv_menu)
    public void onIvMenuTouched() {
        mGallery.goToGallery(this, TabBarActivity.SELECT_IMAGE);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackTouched() {
        finish();
    }

    @OnClick(R.id.iv_add)
    public void onIvAddTouched() {

        if (mType.equals("menu")
                && !mEt_cost.getText().toString().isEmpty()
                && !mEt_name.getText().toString().isEmpty()) {

            String newKey = mMenuApi.getChildMenu().push().getKey();
            Menu newMenu = new Menu(newKey, mEt_name.getText().toString(),
                    Long.valueOf(mEt_cost.getText().toString()),
                    mEt_description.getText().toString(), TabBarActivity.USER.getUserId(),
                    "https://i.pinimg.com/originals/21/b8/ff/21b8ff6b2cc2731d72ccc4b7472fd915.jpg",
                    mEt_currency.getText().toString(),
                    Long.valueOf(mEt_amount.getText().toString()), mEt_unit.getText().toString());

            DialogFragment progress = new ProgressDialogFragment();
            progress.show(getSupportFragmentManager(), "progress");

            if(mUriImage != null) {
                SaveImage saveImage = new SaveImage(this);
                saveImage.saveImageToStorageFirebase(this, mUriImage, progress, newMenu);
            } else {

                MenuApi.getMenuApi().newMenu(this, newKey, newMenu);
                progress.dismiss();
                this.finish();
            }
        } else {

            Toast.makeText(this, "กรุณาใส่ข้อมูลให้ครบด้วยค่ะ!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == TabBarActivity.SELECT_IMAGE) {

                mUriImage = data.getData();
                String strPath = mGallery.getRealPathFromURI(this, mUriImage);

                File fImage = new File(strPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

                mIv_menu.setImageBitmap(imageBitmap);

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
