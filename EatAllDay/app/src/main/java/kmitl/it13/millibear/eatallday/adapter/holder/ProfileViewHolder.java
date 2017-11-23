package kmitl.it13.millibear.eatallday.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it13.millibear.eatallday.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_facebook)
    public TextView tv_facebook;

    @BindView(R.id.tv_name)
    public TextView tv_name;

    @BindView(R.id.iv_image)
    public CircleImageView iv_image;

    @BindView(R.id.tv_description)
    public TextView tv_description;

    @BindView(R.id.iv_edit_profile)
    public ImageView iv_edit_profile;

    @BindView(R.id.et_name)
    public EditText et_name;

    @BindView(R.id.et_description)
    public EditText et_description;

    @BindView(R.id.et_facebook)
    public EditText et_facebook;

    @BindView(R.id.iv_verify)
    public ImageView iv_verify;

    public ProfileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
