package kmitl.it13.millibear.eatallday.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;

/**
 * Created by tiwip on 11/20/2017.
 */

public class FeatureFoodTwoHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_food)
    public CircleImageView iv_food;

    @BindView(R.id.tv_cost)
    public TextView tv_cost;

    @BindView(R.id.tv_name)
    public TextView tv_name;

    @BindView(R.id.btn_add)
    public Button btn_add;

    public FeatureFoodTwoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
