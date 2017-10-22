package kmitl.it13.millibear.eatallday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity
implements View.OnClickListener{

    private TextView mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        bindWidget();
        setWidgetEventListener();
    }

    private void bindWidget(){

        mSignUp = (TextView) findViewById(R.id.tv_sign_up);
    }

    private void setWidgetEventListener(){

        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.tv_sign_up){
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}
