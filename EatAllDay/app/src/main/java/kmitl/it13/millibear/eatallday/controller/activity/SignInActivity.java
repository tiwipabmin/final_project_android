
package kmitl.it13.millibear.eatallday.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
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
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.AuthenticationApi;
import kmitl.it13.millibear.eatallday.model.User;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.controller.fragment.AlertDialogFragment;
import kmitl.it13.millibear.eatallday.controller.fragment.ProgressDialogFragment;

public class SignInActivity extends AppCompatActivity
        implements ValueEventListener {

    private final int SIGN_UP_SUCCESS = 1921;

    final int REQUEST_WRITE_EXTERNAL_PERMISSIONS = 1234;
    final int REQUEST_READ_EXTERNAL_PERMISSIONS = 1235;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    @OnClick(R.id.contain_signIn)
    public void onContainSignInTouched(){
        SplashScreenActivity.hideSoftKeyboard(this);
    }

    private User mUser;
    private DatabaseReference mChildUser;
    private ProgressDialogFragment progress;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        checkPermission();
        ButterKnife.bind(SignInActivity.this);
        initialInstance();

        accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            connectionWithFacebook();
        }

        setUp();
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_PERMISSIONS);
            Toast.makeText(this, "Permission grated", Toast.LENGTH_SHORT).show();
        } else if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_PERMISSIONS);
            Toast.makeText(this, "Permission grated", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialInstance() {

        mChildUser = UserApi.getUserApi().getChildUser();

        progress = new ProgressDialogFragment();

        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = AuthenticationApi.getAuthenticationApi().getFirebaseAuth();
    }

    private void setUp() {

        loginButton.setReadPermissions("email", "public_profile", "user_photos");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                connectionWithFacebook();
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

    private void connectionWithFacebook() {

        progress.show(getSupportFragmentManager(), "progress");
        boolean isError = false;

        Profile profile;
        try {
            profile = Profile.getCurrentProfile();
            hasAccount(profile);

        } catch (NullPointerException ex) {

            Toast.makeText(SignInActivity.this, "กรุณาเชื่อมต่อ facebook อีกครั้ง", Toast.LENGTH_SHORT).show();
            isError = true;
        } catch (Exception ex) {

            Toast.makeText(SignInActivity.this, "กรุณาเชื่อมต่อ facebook อีกครั้ง", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        if(isError){
            LoginManager.getInstance().logOut();
            progress.dismiss();
        }
    }

    private void hasAccount(Profile profile) {

        final UserApi userApi = UserApi.getUserApi();
        final User user = new User(profile.getId(), profile.getName(), profile.getFirstName(), profile.getName(), profile.getProfilePictureUri(500, 500).toString());
        userApi.getChildUser().child(user.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    userApi.newUser(SignInActivity.this, user);
                }
                Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                progress.dismiss();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInTouched() {

        if (!TextUtils.isEmpty(et_email.getText().toString()) && !TextUtils.isEmpty(et_password.getText().toString())) {

            progress.show(getSupportFragmentManager(), "progress");

            firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final FirebaseUser user = firebaseAuth.getCurrentUser();

                                assert user != null;
                                final String uId = user.getUid();

                                mChildUser.orderByChild(uId)
                                        .addListenerForSingleValueEvent(SignInActivity.this);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    DialogFragment alertDialog = new AlertDialogFragment()
                            .newInstance("Email or password invalid.");
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
        startActivityForResult(intent, SIGN_UP_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SIGN_UP_SUCCESS) {

                User user = data.getParcelableExtra("user");
                Intent intent = new Intent(this, TabBarActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.getValue(User.class);
            mUser.setUserId(ds.getKey());
        }

        if (mUser != null) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
