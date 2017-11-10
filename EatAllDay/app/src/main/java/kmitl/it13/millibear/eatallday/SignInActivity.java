
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
import kmitl.it13.millibear.eatallday.api.AuthenticationApi;
import kmitl.it13.millibear.eatallday.api.User;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.fragment.AlertDialogFragment;
import kmitl.it13.millibear.eatallday.fragment.ProgressFragment;

public class SignInActivity extends AppCompatActivity
        implements ValueEventListener {

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

            firebaseAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final FirebaseUser user = firebaseAuth.getCurrentUser();

                                assert user != null;
                                final String uId = user.getUid();

                                Toast.makeText(SignInActivity.this, String.valueOf(uId), Toast.LENGTH_SHORT).show();

                                mChildUser.orderByChild(uId)
                                        .addListenerForSingleValueEvent(SignInActivity.this);

                            } else {

                                Log.i("Access is denied!", String.valueOf(task.getException()));
                                progress.dismiss();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    DialogFragment alertDialog = new AlertDialogFragment()
                            .newInstance("Connection server is denied");
                    alertDialog.show(getSupportFragmentManager(), "alertDialog");
                    progress.dismiss();
                }
            });

        } else {

            DialogFragment alertDialog = new AlertDialogFragment()
                    .newInstance("Please enter your email or password");
            alertDialog.show(getSupportFragmentManager(), "alertDialog");
        }

    }

    private void goToLobby(User user) {
        Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tv_sign_up)
    void onSignUpTouched() {

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.getValue(User.class);
            mUser.setUserId(ds.getKey());
        }

        if (mUser == null) {

            DialogFragment alertDialog = new AlertDialogFragment()
                    .newInstance("email or password is invalid.");
            et_email.setText("");
            et_password.setText("");
            alertDialog.show(getSupportFragmentManager(), "alertDialog");

        } else {
            progress.dismiss();
            goToLobby(mUser);
        }

        if (progress.isVisible()) {
            progress.dismiss();
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
