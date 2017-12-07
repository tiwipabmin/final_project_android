package com.tiwipabmin.eatallday.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tiwipabmin.eatallday.Gallery;
import com.tiwipabmin.eatallday.R;
import com.tiwipabmin.eatallday.SaveImage;
import com.tiwipabmin.eatallday.api.MenuApi;
import com.tiwipabmin.eatallday.controller.fragment.AlertEditMenuDialogFragment;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;
import com.tiwipabmin.eatallday.model.Menu;

public class EditMenuActivity extends AppCompatActivity {

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

    @BindView(R.id.iv_verify)
    ImageView mIv_verify;

    @BindView(R.id.iv_menu)
    CircleImageView mIv_menu;

    private Menu mCurrentMenu;
    private Gallery mGallery;
    private boolean mIsEdit = false;
    private Uri mUriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        ButterKnife.bind(this);

        Intent editMenuIntent = getIntent();
        mCurrentMenu = editMenuIntent.getParcelableExtra("menu");

        initialInstance();
        setUp();
    }

    private void initialInstance() {

        mGallery = new Gallery();
    }

    private void setUp() {

        Glide.with(this).load(mCurrentMenu.getImage()).into(mIv_menu);
        mEt_name.setText(mCurrentMenu.getName());
        mEt_cost.setText(String.valueOf(mCurrentMenu.getCost()));
        mEt_currency.setText(mCurrentMenu.getCurrency());
        mEt_amount.setText(String.valueOf(mCurrentMenu.getAmount()));
        mEt_unit.setText(mCurrentMenu.getUnit());
        mEt_description.setText(mCurrentMenu.getDescription());
    }

    @OnClick(R.id.iv_back)
    public void onImageViewBackTouched() {

        if(mIsEdit){
            AlertEditMenuDialogFragment alertEditMenu = new AlertEditMenuDialogFragment();
            alertEditMenu.show(this.getSupportFragmentManager(), "alertEditMenu");
        } else {
            finish();
        }
    }

    @OnClick(R.id.iv_menu)
    public void onImageViewMenuTouched() {

        mGallery.goToGallery(this, TabBarActivity.SELECT_IMAGE);
    }

    @OnTextChanged(value = R.id.et_name,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextNameOnTextChanged(Editable editable){

        if(!mEt_name.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_cost,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextCostOnTextChanged(Editable editable){

        if(!mEt_cost.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_currency,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextCurrencyOnTextChanged(Editable editable){

        if(!mEt_currency.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_amount,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextAmountTextChanged(Editable editable){

        if(!mEt_amount.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_unit,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextUnitOnTextChanged(Editable editable){

        if(!mEt_unit.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_description,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEditTextDescriptionOnTextChanged(Editable editable){

        if(!mEt_description.getText().toString().isEmpty()) {
            mIsEdit = true;
        }
    }

    @OnClick(R.id.iv_verify)
    public void onImageViewVerifyTouched() {

        if (!mEt_cost.getText().toString().isEmpty() && !mEt_name.getText().toString().isEmpty()) {

            DialogFragment progress = new ProgressDialogFragment();
            progress.show(getSupportFragmentManager(), "progress");

            String menuId = mCurrentMenu.getId();
            Menu updateMenu = new Menu(menuId, mEt_name.getText().toString(),
                    Long.valueOf(mEt_cost.getText().toString()),
                    mEt_description.getText().toString(),
                    TabBarActivity.USER.getUserId(),
                    mCurrentMenu.getImage(), mEt_currency.getText().toString(),
                    Long.valueOf(mEt_amount.getText().toString()),
                    mEt_unit.getText().toString());

            if(mUriImage != null){

                SaveImage saveImage = new SaveImage(this);
                saveImage.saveImageToStorageFirebase(this, mUriImage, progress, updateMenu);
            } else {

                MenuApi.getMenuApi().updateMenu(this, menuId, updateMenu);
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

                mIsEdit = true;

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
