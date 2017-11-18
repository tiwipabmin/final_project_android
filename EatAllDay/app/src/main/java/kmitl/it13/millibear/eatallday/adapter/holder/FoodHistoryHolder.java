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

public class FoodHistoryHolder extends RecyclerView.ViewHolder {

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

    @BindView(R.id.iv_sharing)
    public ImageView iv_sharing;

    public FoodHistoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.iv_sharing)
    public void onIvSharingTouched(){

    }
}
