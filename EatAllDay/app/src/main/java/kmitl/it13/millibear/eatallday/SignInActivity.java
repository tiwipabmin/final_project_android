package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SignInActivity extends AppCompatActivity{

    String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(SignInActivity.this);

    }

    @OnTextChanged(value = R.id.et_email,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEmailInput(Editable editable) {

        mEmail = editable.toString();
    }

    @OnTextChanged(value = R.id.et_email,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterPasswordInput(Editable editable) {

        mPassword = editable.toString();
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInTouched(View view){

        if(!TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPassword)){

        }

    }

    @OnClick(R.id.tv_sign_up)
    void onSignUpTouched(){

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
