package kmitl.it13.millibear.eatallday.controller.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.api.AuthenticationApi;
import kmitl.it13.millibear.eatallday.controller.activity.SignInActivity;
import kmitl.it13.millibear.eatallday.model.User;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.controller.fragment.AlertDialogFragment;
import kmitl.it13.millibear.eatallday.controller.fragment.ProgressFragment;

public class SignUpActivity extends AppCompatActivity {

    DatabaseReference mChildUser;
    UserApi userApi;
    DialogFragment progress;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_facebook)
    EditText et_facebook;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initialInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UserApi.getUserApi().setListener(this);
    }

    void initialInstance(){

        mChildUser = UserApi.getUserApi().getChildUser();

        userApi = UserApi.getUserApi();

        progress = new ProgressFragment();
    }

    @OnClick(R.id.btn_verify)
    void onVerifyButtonTouched() {
        boolean allValid = true;

        if (!isValidEmail(et_email.getText().toString())) {

            et_email.setError("you can't use this email please fill in new email.");
            allValid = false;
        }

        if(!isTextInput(et_facebook.getText().toString())){

            et_facebook.setError("your name can't use special alphabet or numeric.");
            allValid = false;
        }

        if(!isTextInput(et_name.getText().toString())){

            et_name.setError("your name can't use special alphabet or numeric.");
            allValid = false;
        }

        if(!isPasswordInput(et_password.getText().toString())){

            et_password.setError("your password can't use special alphabet and there are 8 character up.");
            allValid = false;
        }

        if(allValid){
            progress.show(getSupportFragmentManager(), "progress");
            createNewAccount(et_name.getText().toString(), et_email.getText().toString(), et_password.getText().toString(), et_facebook.getText().toString());
        }
    }

    @OnClick(R.id.tv_sign_in)
    void onSignInButtonTouched() {

        SignUpActivity.this.finish();
    }

    boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isTextInput(String text){
        if(TextUtils.isEmpty(text)){
            return false;
        }
        char[] textArray = text.toCharArray();
        for (char character : textArray) {
            int ascii = (int) character;
            if (Character.isDigit(character)) {
                return false;
            } else if ((ascii > 32 && ascii < 48) || (ascii > 57 && ascii < 65) || (ascii > 90 && ascii < 97) || (ascii > 122 && ascii < 127)) {
                return false;
            }
        }
        return true;
    }

    boolean isPasswordInput(String password){
        if(TextUtils.isEmpty(password)){
            return false;
        } else if(password.length() < 8){
            return false;
        }
        char[] passwordArray = password.toCharArray();
        for (char character : passwordArray) {
            int ascii = (int) character;
            if ((ascii > 32 && ascii < 48) || (ascii > 57 && ascii < 65) || (ascii > 90 && ascii < 97) || (ascii > 122 && ascii < 127)) {
                return false;
            }
        }
        return true;
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

                                    Toast.makeText(SignUpActivity.this, uId, Toast.LENGTH_SHORT).show();

                                    User newUser = new User(uId, name, email, facebook);

                                    userApi.newUser(SignUpActivity.this, newUser);

                                    goToLobby(newUser);
                                    progress.dismiss();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DialogFragment alertDialog = new AlertDialogFragment()
                        .newInstance("Registration failed.");
                alertDialog.show(getSupportFragmentManager(), "alertDialog");
            }
        });
    }

    private void goToLobby(User newUser){
        Intent intent = new Intent(SignUpActivity.this, TabBarActivity.class);
        intent.putExtra("user", newUser);
        startActivity(intent);
        finish();
    }
}
