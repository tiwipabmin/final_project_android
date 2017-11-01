package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.fragment.ProgressFragment;

public class SignUpActivity extends AppCompatActivity {

    String mEmail, mFacebook, mName, mPassword;
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

    void initialInstance(){

        mChildUser = UserApi.getUserApi().getChildUser();

        userApi = UserApi.getUserApi();

        progress = new ProgressFragment();
    }

    @OnClick(R.id.btn_verify)
    void onVerifyButtonTouched() {
        boolean allValid = true;

        if (!isValidEmail(mEmail)) {

            et_email.setError("you can't use this email please fill in new email.");
            allValid = false;
        }

        if(!isTextInput(mFacebook)){

            et_facebook.setError("your name can't use special alphabet or numeric.");
            allValid = false;
        }

        if(!isTextInput(mName)){

            et_name.setError("your name can't use special alphabet or numeric.");
            allValid = false;
        }

        if(!isPasswordInput(mPassword)){

            et_password.setError("your password can't use special alphabet and there are 8 character up.");
            allValid = false;
        }

        if(allValid){
            progress.show(getSupportFragmentManager(), "progress");
            createAccount();
        }
    }

    @OnClick(R.id.tv_sign_in)
    void onSignInButtonTouched() {

        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(value = R.id.et_email,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEmailInput(Editable editable) {

        mEmail = editable.toString();
    }

    @OnTextChanged(value = R.id.et_facebook,
    callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterFacebookInput(Editable editable){

        mFacebook = editable.toString();
    }

    @OnTextChanged(value = R.id.et_name,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterNameInput(Editable editable){

        mName = editable.toString();
    }

    @OnTextChanged(value = R.id.et_password,
    callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterPasswordInput(Editable editable){

        mPassword = editable.toString();
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

    void createAccount() {

        mChildUser.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long currentId = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    currentId = Long.valueOf(ds.getKey()) + 1;
                }

                userApi.newUser(String.valueOf(currentId), mName, mEmail, mPassword, mFacebook);
                progress.dismiss();
                SignUpActivity.this.finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
