package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.holder.ItemRandomMenuViewHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.ItemShowMenuViewHolder;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.controller.fragment.MenuDetailDialogFragment;
import kmitl.it13.millibear.eatallday.model.Food;

/**
 * Created by tiwip on 11/21/2017.
 */

public class MenusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int FEATURE1 = 0, FEATURE2 = 1;

    private Context mContext;
    private ArrayList<Food> mMenu;
    private MenusAdapter.MenusListener mListener;
    private int mFeature = 0;

    public interface MenusListener {

        public void onBtnAddTouched(int position);
    }

    public MenusAdapter(Context context, ArrayList<Food> mMenu, MenusAdapter.MenusListener listener, int feature) {
        this.mContext = context;
        this.mMenu = mMenu;
        this.mListener = listener;
        this.mFeature = feature;
    }

    public MenusAdapter(Context context, ArrayList<Food> mMenu, int mFeature) {
        this.mContext = context;
        this.mMenu = mMenu;
        this.mFeature = mFeature;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(mContext);


        switch (viewType){
            case FEATURE1:
                View itemView1 = inflater.inflate(R.layout.item_show_menu, null);
                viewHolder = new ItemShowMenuViewHolder(itemView1);
                break;
            case FEATURE2:
                View itemView2 = inflater.inflate(R.layout.item_random_menu, null);
                viewHolder = new ItemRandomMenuViewHolder(itemView2);
                break;
            default:
                View itemView = inflater.inflate(R.layout.item_show_menu, null);
                viewHolder = new ItemShowMenuViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case FEATURE1:
                ItemShowMenuViewHolder itemShowMenuViewHolder = (ItemShowMenuViewHolder) holder;
                configureItemShowMenuViewHolder(itemShowMenuViewHolder, position);
                break;
            case FEATURE2:
                ItemRandomMenuViewHolder itemRandomMenuViewHolder = (ItemRandomMenuViewHolder) holder;
                configureItemRandomViewHolder(itemRandomMenuViewHolder, position);
                break;
            default: break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mFeature == 0){
            return FEATURE1;
        } else if(mFeature == 1){
            return FEATURE2;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }

    private void configureItemShowMenuViewHolder(ItemShowMenuViewHolder itemShowMenuViewHolder, final int position) {
        Glide.with(mContext).load(mMenu.get(position).getImage()).into(itemShowMenuViewHolder.iv_food);
        itemShowMenuViewHolder.tv_name.setText(mMenu.get(position).getName());

        itemShowMenuViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment detailFoodDialogFragment = new MenuDetailDialogFragment().newInstance(mMenu.get(position));
                detailFoodDialogFragment.show(((TabBarActivity)mContext).getSupportFragmentManager(), "detailFood");
            }
        });
    }

    private void configureItemRandomViewHolder(ItemRandomMenuViewHolder itemRandomMenuViewHolder, final int position){
        Food food = mMenu.get(position);

        Glide.with(mContext).load(food.getImage()).into(itemRandomMenuViewHolder.iv_food);
        itemRandomMenuViewHolder.tv_name.setText(food.getName());

        String cost = String.valueOf(food.getCost()) + " " + food.getCurrency() + " / " + food.getAmount() + " " + food.getUnit();

        itemRandomMenuViewHolder.tv_cost.setText(cost);

        itemRandomMenuViewHolder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBtnAddTouched(position);
            }
        });
    }

}
