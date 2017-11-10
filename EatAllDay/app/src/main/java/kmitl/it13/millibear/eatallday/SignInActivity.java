package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.api.AuthenticationApi;
import kmitl.it13.millibear.eatallday.api.User;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.fragment.AlertDialogFragment;
import kmitl.it13.millibear.eatallday.fragment.ProgressFragment;

public class SignInActivity extends AppCompatActivity {

    public static final int SIGN_IN_ACTIVITY_NUMBER = 1901;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    String mEmail, mPassword;
    User mUser;
    DatabaseReference mChildUser;
    ProgressFragment progress;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(SignInActivity.this);

        initialInstance();
        setUp();
    }

    private void initialInstance() {

        mChildUser = UserApi.getUserApi().getChildUser();

        progress = new ProgressFragment();

        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = AuthenticationApi.getAuthenticationApi().getFirebaseAuth();
    }

    private void setUp() {

        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken.getCurrentAccessToken().getDeclinedPermissions();
                Toast.makeText(SignInActivity.this, String.valueOf(loginResult.getRecentlyGrantedPermissions()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                LoginManager.getInstance().logOut();
                Toast.makeText(SignInActivity.this, String.valueOf(exception), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnTextChanged(value = R.id.et_email,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEmailInput(Editable editable) {

        mEmail = editable.toString();
    }

    @OnTextChanged(value = R.id.et_password,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterPasswordInput(Editable editable) {

        mPassword = editable.toString();
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInTouched() {

        if (!TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPassword)) {
            progress.show(getSupportFragmentManager(), "progress");
            FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Access complete!", "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(SignInActivity.this, String.valueOf(task), Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("Access is denied!", String.valueOf(task.getException()));
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, "Task Null",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//            mChildUser.orderByChild("email")
//                    .equalTo(mEmail)
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                mUser = ds.getValue(User.class);
//                                mUser.setUserId(String.valueOf(ds.getKey()));
//                            }
//
//                            if (mUser == null) {
//
//                                DialogFragment alertDialog = new AlertDialogFragment()
//                                        .newInstance("email or password is invalid.");
//                                et_email.setText("");
//                                et_password.setText("");
//                                alertDialog.show(getSupportFragmentManager(), "alertDialog");
//
//                            } else {
//                                progress.dismiss();
//                                authentication(mUser.getPassword());
//                            }
//
//                            if (progress.isVisible()) {
//                                progress.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(SignInActivity.this
//                                    , "Database error: ".concat(databaseError.getMessage())
//                                    , Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    });
        } else

        {

            DialogFragment alertDialog = new AlertDialogFragment()
                    .newInstance("Please enter your email or password");
            alertDialog.show(getSupportFragmentManager(), "alertDialog");
        }

    }

    private void goToLobby(FirebaseUser user) {
        Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
        intent.putExtra("user", mUser);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tv_sign_up)
    void onSignUpTouched() {

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_IN_ACTIVITY_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_ACTIVITY_NUMBER) {
            if (resultCode == RESULT_OK) {

                Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
                startActivity(intent);

                SignInActivity.this.finish();
            }
        }
    }
}
