package com.tiwipabmin.eatallday.controller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tiwipabmin.eatallday.api.UserApi;
import com.tiwipabmin.eatallday.model.ValidationResult;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.tiwipabmin.eatallday.validation.CheckNetworkConnection;
import com.tiwipabmin.eatallday.validation.EmailValidation;
import com.tiwipabmin.eatallday.validation.FacebookValidation;
import com.tiwipabmin.eatallday.validation.NameValidation;
import com.tiwipabmin.eatallday.validation.PasswordValidation;
import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.model.User;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText mEt_email;

    @BindView(R.id.et_facebook)
    EditText mEt_facebook;

    @BindView(R.id.et_name)
    EditText mEt_name;

    @BindView(R.id.et_password)
    EditText mEt_password;

    @BindView(R.id.et_verify_password)
    EditText mEt_verify_password;

    private UserApi mUserApi;
    private DialogFragment mProgress;
    private boolean mIsTouched = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initialInstance();
    }

    void initialInstance(){

        mUserApi = UserApi.getUserApi();

        mProgress = new ProgressDialogFragment();
    }

    @OnClick(R.id.btn_verify)
    void onVerifyButtonTouched() {
        boolean allValid = true;

        NameValidation nameValidation = new NameValidation();
        EmailValidation emailValidation = new EmailValidation();
        PasswordValidation passwordValidation = new PasswordValidation();
        FacebookValidation facebookValidation = new FacebookValidation();

        ValidationResult nameResult = nameValidation.validate(mEt_name.getText().toString());
        ValidationResult emailResult = emailValidation.validate(mEt_email.getText().toString());
        ValidationResult passwordResult = passwordValidation.validate(mEt_password.getText().toString());
        ValidationResult facebookResult = facebookValidation.validate(mEt_facebook.getText().toString());

        if(!nameResult.getResult()){

            mEt_name.setError("ชื่อของคุณไม่สามารถใช้ตัวอักษรพิเศษหรือตัวเลขได้.");
            allValid = false;
        }

        if (!emailResult.getResult()) {

            mEt_email.setError("อีเมล์นี้ไม่สามารถใช้ได้ กรุณากรอกใหม่.");
            allValid = false;
        }

        if(!passwordResult.getResult()){

            mEt_password.setError("รหัสผ่านของคุณไม่สามารถใช้ตัวอักษรพิเศษได้และต้องมีตัวอักษรอย่างน้อย 8 ตัวขึ้นไป.");
            allValid = false;
        }

        if(!mEt_password.getText().toString().equals(mEt_verify_password.getText().toString())){

            mEt_verify_password.setError("รหัสผ่านของคุณไม่ตรงกัน.");
            allValid = false;
        }

        if(!facebookResult.getResult()){

            mEt_facebook.setError("facebook ของคุณไม่ถูกต้อง.");
            allValid = false;
        }

        if(allValid && mIsTouched){
            mIsTouched = false;
            mProgress.show(getSupportFragmentManager(), "mProgress");
            createNewAccount(mEt_name.getText().toString(), mEt_email.getText().toString(), mEt_password.getText().toString(), mEt_facebook.getText().toString());
        }
    }

    @OnClick(R.id.tv_sign_in)
    void onSignInButtonTouched() {
        SignUpActivity.this.finish();
    }

    void createNewAccount(final String name, final String email, String password, final String facebook) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    assert user != null;
                                    String uId = user.getUid();

                                    User newUser = new User(uId, name, email, facebook);

                                    mUserApi.newUser(SignUpActivity.this, newUser);
                                    Toast.makeText(SignUpActivity.this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_SHORT).show();

                                    goToLobby(newUser);
                                    mProgress.dismiss();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    mIsTouched = true;
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mIsTouched = true;
                mProgress.dismiss();
            }
        });
    }

    private void goToLobby(User newUser){
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        intent.putExtra("user", newUser);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
