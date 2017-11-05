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
 * Created by tiwip on 11/3/2017.
 */

public class ProfileHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_facebook)
    public TextView tv_facebook;

    @BindView(R.id.tv_name)
    public TextView tv_name;

    @BindView(R.id.iv_image)
    public CircleImageView iv_image;

    @BindView(R.id.tv_description)
    public TextView tv_description;

    public ProfileHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
