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
import com.google.firebase.database.DatabaseReference;
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
    EditText et_email;

    @BindView(R.id.et_facebook)
    EditText et_facebook;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_verify_password)
    EditText et_verify_password;

    private CheckNetworkConnection mCheckNetworkConnection;
    private UserApi userApi;
    private DialogFragment progress;
    private boolean isTouched = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initialInstance();
    }

    void initialInstance(){

        userApi = UserApi.getUserApi();

        progress = new ProgressDialogFragment();

        mCheckNetworkConnection = CheckNetworkConnection
                .getCheckNetworkConnection();
    }

    @OnClick(R.id.btn_verify)
    void onVerifyButtonTouched() {
        boolean allValid = true;

        NameValidation nameValidation = new NameValidation();
        EmailValidation emailValidation = new EmailValidation();
        PasswordValidation passwordValidation = new PasswordValidation();
        FacebookValidation facebookValidation = new FacebookValidation();

        ValidationResult nameResult = nameValidation.validate(et_name.getText().toString());
        ValidationResult emailResult = emailValidation.validate(et_email.getText().toString());
        ValidationResult passwordResult = passwordValidation.validate(et_password.getText().toString());
        ValidationResult facebookResult = facebookValidation.validate(et_facebook.getText().toString());

        if(!nameResult.getResult()){

            et_name.setError("your name can't be special character or numeric.");
            allValid = false;
        }

        if (!emailResult.getResult()) {

            et_email.setError("you can't be this email please fill in new email.");
            allValid = false;
        }

        if(!passwordResult.getResult()){

            et_password.setError("your password can't be special character and there are 8 character up.");
            allValid = false;
        }

        if(!et_password.getText().toString().equals(et_verify_password.getText().toString())){

            et_verify_password.setError("your password verify don't pass.");
            allValid = false;
        }

        if(!facebookResult.getResult()){

            et_facebook.setError("your facebook invalid.");
            allValid = false;
        }

        if(allValid && isTouched && mCheckNetworkConnection
                .isConnected()){
            isTouched = false;
            progress.show(getSupportFragmentManager(), "progress");
            createNewAccount(et_name.getText().toString(), et_email.getText().toString(), et_password.getText().toString(), et_facebook.getText().toString());
        } else {

            Toast.makeText(
                    this,
                    "ไม่มีการเชื่อมต่อกับอินเทอร์เน็ต.",
                    Toast.LENGTH_SHORT).show();
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

                                    userApi.newUser(SignUpActivity.this, newUser);
                                    Toast.makeText(SignUpActivity.this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_SHORT).show();

                                    goToLobby(newUser);
                                    progress.dismiss();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    isTouched = true;
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isTouched = true;
                progress.dismiss();
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
