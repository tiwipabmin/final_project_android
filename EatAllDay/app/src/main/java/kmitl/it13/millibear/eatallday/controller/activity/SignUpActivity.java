package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.EmailValidation;
import kmitl.it13.millibear.eatallday.FacebookValidation;
import kmitl.it13.millibear.eatallday.NameValidation;
import kmitl.it13.millibear.eatallday.PasswordValidation;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.model.User;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.controller.fragment.AlertDialogFragment;
import kmitl.it13.millibear.eatallday.controller.fragment.ProgressDialogFragment;
import kmitl.it13.millibear.eatallday.model.ValidationResult;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference mChildUser;
    UserApi userApi;
    DialogFragment progress;
    private boolean isTouched = true;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initialInstance();
    }

    void initialInstance(){

        mChildUser = UserApi.getUserApi().getChildUser();

        userApi = UserApi.getUserApi();

        progress = new ProgressDialogFragment();
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

        if(allValid && isTouched){
            isTouched = false;
            progress.show(getSupportFragmentManager(), "progress");
            createNewAccount(et_name.getText().toString(), et_email.getText().toString(), et_password.getText().toString(), et_facebook.getText().toString());
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
