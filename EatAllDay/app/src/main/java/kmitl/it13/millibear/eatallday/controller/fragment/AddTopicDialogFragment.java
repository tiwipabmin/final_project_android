package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.HistoryApi;
import kmitl.it13.millibear.eatallday.controller.activity.RandomActivity;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.model.Food;
import kmitl.it13.millibear.eatallday.model.History;

public class AddTopicDialogFragment extends DialogFragment {

    @BindView(R.id.et_topics)
    EditText et_topics;

    private Food mFood;
    private Context mContext;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_topic, null);
        ButterKnife.bind(this, view);

        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkTouched(){
        if(!et_topics.getText().toString().isEmpty()){
            String newKey = HistoryApi.getHistoryApi().getChildHistory().push().getKey();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.US);
            String date = fmt.format(new Date());

            History newHistory = new History(newKey, TabBarActivity.USER_ID, mFood.getName(), et_topics.getText().toString(), mFood.getImage(), mFood.getCost(), mFood.getCurrency(), mFood.getAmount(), mFood.getUnit(), date, "food");
            HistoryApi.getHistoryApi().newHistory(newKey, newHistory);
            this.dismiss();
            ((RandomActivity)mContext).finish();
            Toast.makeText(getContext(), "บันทึกประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
        }
    }

    public static AddTopicDialogFragment newInstance(Context context, Food food) {
        Bundle args = new Bundle();
        args.putParcelable("food", food);
        AddTopicDialogFragment fragment = new AddTopicDialogFragment();
        fragment.setContext(context);
        fragment.setArguments(args);
        return fragment;
    }
}
