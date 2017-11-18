package kmitl.it13.millibear.eatallday.adapter.holder;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.SupportActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.controller.activity.FoodItemActivity;
import kmitl.it13.millibear.eatallday.controller.fragment.AlertDialogFragment;

public class FoodHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_food)
    public CircleImageView iv_food;

    @BindView(R.id.tv_cost)
    public TextView tv_cost;

    @BindView(R.id.tv_name)
    public TextView tv_name;

    @BindView(R.id.btn_add)
    public Button btn_add;

    public FoodHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
