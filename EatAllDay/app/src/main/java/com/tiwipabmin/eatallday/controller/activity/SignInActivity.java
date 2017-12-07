
package com.tiwipabmin.eatallday.controller.activity;

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
import com.tiwipabmin.eatallday.api.UserApi;
import com.tiwipabmin.eatallday.api.AuthenticationApi;
import com.tiwipabmin.eatallday.controller.fragment.AlertDialogFragment;
import com.tiwipabmin.eatallday.controller.fragment.ProgressDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

import com.tiwipabmin.eatallday.model.User;
import com.tiwipabmin.eatallday.validation.CheckNetworkConnection;

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

    private User mUser;
    private DatabaseReference mChildUser;
    private ProgressDialogFragment mProgress;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private AccessToken mAccessToken;
    private boolean mIsTouched = true;
    private CheckNetworkConnection mCheckNetworkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        checkPermission();
        ButterKnife.bind(SignInActivity.this);
        initialInstance();

        mAccessToken = AccessToken.getCurrentAccessToken();

        if (!mCheckNetworkConnection
                .isConnected()){

            Toast.makeText(
                    this,
                    "ไม่มีการเชื่อมต่อกับอินเทอร์เน็ต.",
                    Toast.LENGTH_SHORT).show();

            LoginManager.getInstance().logOut();
        }

        if (mAccessToken != null
                && mCheckNetworkConnection
                .isConnected()) {
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

        mProgress = new ProgressDialogFragment();

        mCallbackManager = CallbackManager.Factory.create();

        mFirebaseAuth = AuthenticationApi.getAuthenticationApi().getFirebaseAuth();

        mCheckNetworkConnection = CheckNetworkConnection.getCheckNetworkConnection();

    }

    private void setUp() {

        loginButton.setReadPermissions("email", "public_profile", "user_photos");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
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

        mProgress.show(getSupportFragmentManager(), "mProgress");
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

        if (isError) {
            LoginManager.getInstance().logOut();
            mProgress.dismiss();
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
                mProgress.dismiss();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.iv_information)
    void onIvInformationTouched(){

        Intent intent = new Intent(this, AboutApplicationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInTouched() {

        if (!TextUtils.isEmpty(et_email.getText().toString())
                && !TextUtils.isEmpty(et_password.getText().toString())
                && mIsTouched
                && mCheckNetworkConnection
                .isConnected()) {

            mIsTouched = false;

            mProgress.show(getSupportFragmentManager(), "mProgress");

            mFirebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                                assert user != null;
                                final String uId = user.getUid();

                                mChildUser.orderByChild("userId")
                                        .equalTo(uId)
                                        .addListenerForSingleValueEvent(SignInActivity.this);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    DialogFragment alertDialog = new AlertDialogFragment()
                            .newInstance("Email or password invalid.");
                    alertDialog.show(getSupportFragmentManager(), "alertDialog");
                    mIsTouched = true;
                    mProgress.dismiss();
                }
            });

        } else if(mCheckNetworkConnection
                .isConnected()){

            DialogFragment alertDialog = new AlertDialogFragment()
                    .newInstance("Please enter your email or password");
            alertDialog.show(getSupportFragmentManager(), "alertDialog");
        } else {

            Toast.makeText(
                    this,
                    "ไม่มีการเชื่อมต่อกับอินเทอร์เน็ต.",
                    Toast.LENGTH_SHORT).show();
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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

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

            mProgress.dismiss();
            goToLobby(mUser);

        }

        if (mProgress.isVisible()) {
            mProgress.dismiss();
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
