package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.model.Food;

/**
 * Created by tiwip on 11/19/2017.
 */

public class TopicsDialogFragment extends DialogFragment {

    @BindView(R.id.et_topics)
    EditText et_topics;

    private Food mFood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mFood = args.getParcelable("food");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_topics, null);
        ButterKnife.bind(this, view);

        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkTouched(){
        if(!et_topics.getText().toString().isEmpty()){

        }
    }

    public static TopicsDialogFragment newInstance(Food food) {
        Bundle args = new Bundle();
        args.putParcelable("food", food);
        TopicsDialogFragment fragment = new TopicsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
