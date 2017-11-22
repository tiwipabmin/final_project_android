package kmitl.it13.millibear.eatallday.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;

public class RandomMenuHistoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_foodImage)
    public ImageView iv_foodImage;

    @BindView(R.id.tv_topic)
    public TextView tv_topic;

    @BindView(R.id.tv_cost)
    public TextView tv_cost;

    @BindView(R.id.tv_piece)
    public TextView tv_piece;

    @BindView(R.id.tv_foodName)
    public TextView tv_foodName;

    @BindView(R.id.iv_userImage)
    public CircleImageView iv_userImage;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    @BindView(R.id.iv_config)
    public ImageView iv_config;

    @BindView(R.id.food_history)
    public LinearLayout layout;

    @BindView(R.id.tv_date)
    public TextView tv_date;

    public RandomMenuHistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
