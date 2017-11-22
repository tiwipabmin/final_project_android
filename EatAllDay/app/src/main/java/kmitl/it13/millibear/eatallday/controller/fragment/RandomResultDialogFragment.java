package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.model.Food;

public class RandomResultDialogFragment extends DialogFragment {

    @BindView(R.id.iv_food)
    ImageView iv_food;

    @BindView(R.id.tv_foodName)
    TextView tv_foodName;

    private Food mFood;
    private Context mContext;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mFood = args.getParcelable("food");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_random_result, null);

        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(mFood.getImage()).into(iv_food);
        tv_foodName.setText(mFood.getName());
        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.btn_accept)
    public void onBtnAcceptTouched(){
        DialogFragment topicsDialog = new AddTopicDialogFragment().newInstance(mContext, mFood);
        topicsDialog.setCancelable(false);
        topicsDialog.show(getActivity().getSupportFragmentManager(), "topicsDialog");
        dismiss();
    }

    public static RandomResultDialogFragment newInstance(Context context, Food food) {
        Bundle args = new Bundle();
        args.putParcelable("food", food);
        RandomResultDialogFragment fragment = new RandomResultDialogFragment();
        fragment.setContext(context);
        fragment.setArguments(args);
        return fragment;
    }
}
