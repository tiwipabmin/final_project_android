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
import com.tiwipabmin.eatallday.api.FoodApi;
import com.tiwipabmin.eatallday.controller.fragment.AlertEditMenuDialogFragment;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;
import com.tiwipabmin.eatallday.model.Food;

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

    private Food mCurrentMenu;
    private Gallery gallery;
    private boolean isEdit = false;
    private Uri uriImage;

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

        gallery = new Gallery();
    }

    private void setUp() {

        Glide.with(this).load(mCurrentMenu.getImage()).into(iv_menu);
        et_name.setText(mCurrentMenu.getName());
        et_cost.setText(String.valueOf(mCurrentMenu.getCost()));
        et_currency.setText(mCurrentMenu.getCurrency());
        et_amount.setText(String.valueOf(mCurrentMenu.getAmount()));
        et_unit.setText(mCurrentMenu.getUnit());
        et_description.setText(mCurrentMenu.getDescription());
    }

    @OnClick(R.id.iv_back)
    public void onIvBackTouched() {

        if(isEdit){
            AlertEditMenuDialogFragment alertEditMenu = new AlertEditMenuDialogFragment();
            alertEditMenu.show(this.getSupportFragmentManager(), "alertEditMenu");
        } else {
            finish();
        }
    }

    @OnClick(R.id.iv_menu)
    public void onIvMenuTouched() {

        gallery.goToGallery(this, TabBarActivity.SELECT_IMAGE);
    }

    @OnTextChanged(value = R.id.et_name,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtNameOnTextChanged(Editable editable){

        if(!et_name.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_cost,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtCostOnTextChanged(Editable editable){

        if(!et_cost.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_currency,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtCurrencyOnTextChanged(Editable editable){

        if(!et_currency.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_amount,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtAmountTextChanged(Editable editable){

        if(!et_amount.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_unit,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtUnitOnTextChanged(Editable editable){

        if(!et_unit.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnTextChanged(value = R.id.et_description,
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void onEtDescriptionOnTextChanged(Editable editable){

        if(!et_description.getText().toString().isEmpty()) {
            isEdit = true;
        }
    }

    @OnClick(R.id.iv_verify)
    public void onIvVerifyTouched() {

        if (!et_cost.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty()) {

            DialogFragment progress = new ProgressDialogFragment();
            progress.show(getSupportFragmentManager(), "progress");

            String menuId = mCurrentMenu.getId();
            Food updateMenu = new Food(menuId, et_name.getText().toString(),
                    Long.valueOf(et_cost.getText().toString()),
                    et_description.getText().toString(),
                    TabBarActivity.USER.getUserId(),
                    mCurrentMenu.getImage(), et_currency.getText().toString(),
                    Long.valueOf(et_amount.getText().toString()),
                    et_unit.getText().toString());

            if(uriImage != null){

                SaveImage saveImage = new SaveImage(this);
                saveImage.saveImageToStorageFirebase(this, uriImage, progress, updateMenu);
            } else {

                FoodApi.getFoodApi().updateMenu(this, menuId, updateMenu);
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

                uriImage = data.getData();
                String strPath = gallery.getRealPathFromURI(this, uriImage);

                File fImage = new File(strPath);
                Bitmap imageBitmap = BitmapFactory.decodeFile(fImage.getAbsolutePath());

                iv_menu.setImageBitmap(imageBitmap);

                isEdit = true;

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
