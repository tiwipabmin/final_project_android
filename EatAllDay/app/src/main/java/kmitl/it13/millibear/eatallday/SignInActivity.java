package kmitl.it13.millibear.eatallday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SignInActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnTextChanged(R.id.et_email)
    public void onTextChanged(CharSequence text) {
        String featureName = text.toString();
        Toast.makeText(this, "name : " + featureName, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_sign_in)
    public void signIn(){
        Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show();
    }
}
