package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.model.Food;

public class DetailFoodDialogFragment extends DialogFragment {

    @BindView(R.id.tv_cost)
    TextView tv_cost;

    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.iv_food)
    CircleImageView iv_food;

    @BindView(R.id.tv_title)
    TextView tv_title;

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_detail_food, null);
        ButterKnife.bind(this, view);

        tv_title.setText(mFood.getName());
        tv_cost.setText(String.valueOf(mFood.getCost()).concat(" Bath / 1 Piece"));
        tv_description.setText(mFood.getDescription());
        Glide.with(getActivity()).load(mFood.getImage()).into(iv_food);

        alertDialog.setView(view);

        return alertDialog.create();
    }

    @OnClick(R.id.iv_cancel)
    public void onIvCancelTouched(){
        this.dismiss();
    }

    public static DetailFoodDialogFragment newInstance(Food food) {
        Bundle args = new Bundle();
        DetailFoodDialogFragment fragment = new DetailFoodDialogFragment();
        args.putParcelable("food", food);
        fragment.setArguments(args);
        return fragment;
    }
}
