package com.tiwipabmin.eatallday.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import com.tiwipabmin.eatallday.R;

public class ItemShowMenuViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_food)
    public CircleImageView iv_food;

    @BindView(R.id.tv_name)
    public TextView tv_name;

    @BindView(R.id.cv_item)
    public CardView cv_item;

    @BindView(R.id.iv_config)
    public ImageView iv_config;

    public ItemShowMenuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
