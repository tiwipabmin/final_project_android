package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kmitl.it13.millibear.eatallday.api.ConnectServer;
import kmitl.it13.millibear.eatallday.api.User;
import kmitl.it13.millibear.eatallday.fragment.ProgressFragment;

public class SignInActivity extends AppCompatActivity {

    String mEmail, mPassword;
    User mUser;
    DatabaseReference mDatabase;
    ProgressFragment progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(SignInActivity.this);

        initialInstance();
    }

    private void initialInstance() {

        mDatabase = ConnectServer.getConnectServer().getDatabase();

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
            mDatabase.child("user")
                    .orderByChild("email")
                    .equalTo(mEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                mUser = ds.getValue(User.class);
                                mUser.setUserId(String.valueOf(ds.getKey()));
                            }

                            if(mUser == null){
                                Toast.makeText(SignInActivity.this
                                        , "Error: Could not fetch user."
                                        , Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                progress.dismiss();
                                authentication(mUser.getPassword());
                            }

                            if(progress.isVisible()) {
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
        }

    }

    private void authentication(String password){
        if(mPassword.equals(password)){
            Intent intent = new Intent(SignInActivity.this, LobbyRoomActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_sign_up)
    void onSignUpTouched() {

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
