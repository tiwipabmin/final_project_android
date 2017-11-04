package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.adapter.PostAdapter;
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

    String mEmail, mPassword;
    User mUser;
    DatabaseReference mChildUser;
    ProgressFragment progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(SignInActivity.this);

        initialInstance();
    }

    private void initialInstance() {

        mChildUser = UserApi.getUserApi().getChildUser();

        progress = new ProgressFragment();
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
    void onSignInTouched(View view) {

        if (!TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPassword)) {
            progress.show(getSupportFragmentManager(), "progress");
            mChildUser.orderByChild("email")
                    .equalTo(mEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mUser = ds.getValue(User.class);
                                mUser.setUserId(String.valueOf(ds.getKey()));
                            }

                            if (mUser == null) {

                                DialogFragment alertDialog = new AlertDialogFragment()
                                        .newInstance("email or password is invalid.");
                                et_email.setText("");
                                et_password.setText("");
                                alertDialog.show(getSupportFragmentManager(), "alertDialog");

                            } else {
                                progress.dismiss();
                                authentication(mUser.getPassword());
                            }

                            if (progress.isVisible()) {
                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignInActivity.this
                                    , "Database error: ".concat(databaseError.getMessage())
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else {

            DialogFragment alertDialog = new AlertDialogFragment()
                    .newInstance("Please enter your email or password");
            alertDialog.show(getSupportFragmentManager(), "alertDialog");
        }

    }

    private void authentication(String password) {
        if (mPassword.equals(password)) {
            Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.tv_sign_up)
    void onSignUpTouched() {

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_IN_ACTIVITY_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_ACTIVITY_NUMBER) {
            if (resultCode == RESULT_OK) {

                Intent intent = new Intent(SignInActivity.this, TabBarActivity.class);
                startActivity(intent);

                SignInActivity.this.finish();
            }
        }
    }
}
