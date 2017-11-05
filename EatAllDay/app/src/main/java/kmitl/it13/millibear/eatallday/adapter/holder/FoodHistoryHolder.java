package kmitl.it13.millibear.eatallday.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;

/**
 * Created by tiwip on 11/5/2017.
 */

public class FoodHistoryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_foodImage)
    public ImageView iv_foodImage;

    @BindView(R.id.tv_topic)
    public TextView tv_topic;

    @BindView(R.id.tv_cost)
    public TextView tv_cost;

    @BindView(R.id.tv_number_of_people)
    public TextView tv_number_of_people;

    @BindView(R.id.tv_piece)
    public TextView tv_piece;

    @BindView(R.id.tv_foodName)
    public TextView tv_foodName;

    @BindView(R.id.iv_userImage)
    public CircleImageView iv_userImage;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    public FoodHistoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
