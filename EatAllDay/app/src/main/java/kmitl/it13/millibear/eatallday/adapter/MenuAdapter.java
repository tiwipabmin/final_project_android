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
import kmitl.it13.millibear.eatallday.adapter.holder.FeatureFoodOneHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.FeatureFoodTwoHolder;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.controller.fragment.DetailFoodDialogFragment;
import kmitl.it13.millibear.eatallday.model.Food;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int FEATURE1 = 0, FEATURE2 = 1;

    private Context mContext;
    private ArrayList<Food> mMenu;
    private MenuListener mListener;
    private int mFeature = 0;

    public interface MenuListener {

        public void onBtnAddTouched(int position);
    }

    public MenuAdapter(Context context, ArrayList<Food> mMenu, MenuListener listener, int feature) {
        this.mContext = context;
        this.mMenu = mMenu;
        this.mListener = listener;
        this.mFeature = feature;
    }

    public MenuAdapter(Context context, ArrayList<Food> mMenu, int mFeature) {
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
                View itemView1 = inflater.inflate(R.layout.item_feature_food_one, null);
                viewHolder = new FeatureFoodOneHolder(itemView1);
                break;
            case FEATURE2:
                View itemView2 = inflater.inflate(R.layout.item_feature_food_two, null);
                viewHolder = new FeatureFoodTwoHolder(itemView2);
                break;
            default:
                View itemView = inflater.inflate(R.layout.item_feature_food_one, null);
                viewHolder = new FeatureFoodOneHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case FEATURE1:
                FeatureFoodOneHolder featureFoodOneHolder = (FeatureFoodOneHolder) holder;
                configureFeatureFoodOneViewHolder(featureFoodOneHolder, position);
                break;
            case FEATURE2:
                FeatureFoodTwoHolder featureFoodTwoHolder = (FeatureFoodTwoHolder) holder;
                configureFeatureFoodTwoViewHolder(featureFoodTwoHolder, position);
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

    private void configureFeatureFoodOneViewHolder(FeatureFoodOneHolder featureFoodOneHolder, final int position) {
        Glide.with(mContext).load(mMenu.get(position).getImage()).into(featureFoodOneHolder.iv_food);
        featureFoodOneHolder.tv_name.setText(mMenu.get(position).getName());

        featureFoodOneHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment detailFoodDialogFragment = new DetailFoodDialogFragment().newInstance(mMenu.get(position));
                detailFoodDialogFragment.show(((TabBarActivity)mContext).getSupportFragmentManager(), "detailFood");
            }
        });
    }

    private void configureFeatureFoodTwoViewHolder(FeatureFoodTwoHolder featureFoodTwoHolder, final int position){
        Food food = mMenu.get(position);

        Glide.with(mContext).load(food.getImage()).into(featureFoodTwoHolder.iv_food);
        featureFoodTwoHolder.tv_name.setText(food.getName());

        String cost = String.valueOf(food.getCost()) + " " + food.getCurrency() + " / " + food.getAmount() + " " + food.getUnit();

        featureFoodTwoHolder.tv_cost.setText(cost);

        featureFoodTwoHolder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBtnAddTouched(position);
            }
        });
    }

}
