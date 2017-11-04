package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.holder.FoodHistoryHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.ProfileHolder;
import kmitl.it13.millibear.eatallday.api.User;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PROFILE = 0, FOOD = 1, RESTAURANT = 2, ALL = 3;

    private Context mContext;
    private User mUser;
    private List<Object> data;
    private List<String> viewType;

    public PostAdapter(Context mContext, User mUser, List<Object> data, List<String> viewType) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.data = data;
        this.viewType = viewType;
    }

    public PostAdapter(Context mContext, List<String> viewType) {
        this.mContext = mContext;
        this.viewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Lifecycle", "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(mContext);


        switch (viewType){
            case PROFILE:
                View itemView1 = inflater.inflate(R.layout.item_profile, null);
                viewHolder = new ProfileHolder(itemView1);
                break;
            case FOOD:
                View itemView2 = inflater.inflate(R.layout.item_food_history, null);
                viewHolder = new FoodHistoryHolder(itemView2);
                break;
            default:
                View itemView = inflater.inflate(R.layout.item_food_history, null);
                viewHolder = new FoodHistoryHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("Lifecycle", "onBindViewHolder");

        switch (holder.getItemViewType()){
            case PROFILE:
                ProfileHolder profileHolder = (ProfileHolder) holder;
                configureProfileViewHolder(profileHolder, position);
                break;
            case FOOD:
                FoodHistoryHolder foodHistoryHolder = (FoodHistoryHolder) holder;
                configureFoodHistoryViewHolder(foodHistoryHolder, position);
                break;
            default: break;
        }
    }

    @Override
    public int getItemCount() {
        Log.i("Lifecycle", "getItemCount : ");
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(viewType.get(position).equals("profile")){
            return PROFILE;
        } else if(viewType.get(position).equals("food")){
            return FOOD;
        } else if(viewType.get(position).equals("restaurant")){
            return RESTAURANT;
        } else if(viewType.get(position).equals("all")){
            return ALL;
        }
        return -1;
    }

    private void configureProfileViewHolder(ProfileHolder profileHolder, int position){

    }

    private void configureFoodHistoryViewHolder(FoodHistoryHolder foodHistoryHolder, int position){

    }

}
