package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.holder.FoodHistoryHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.ProfileHolder;
import kmitl.it13.millibear.eatallday.model.History;
import kmitl.it13.millibear.eatallday.model.User;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PROFILE = 0, FOOD = 1, RESTAURANT = 2, ALL = 3;

    private Context mContext;
    private User mUser;
    private List<History> mStorage;
    private List<String> mViewType;
    private boolean isLoadingAdded = false;

    public PostAdapter(Context mContext, User mUser, List<History> mStorage, List<String> mViewType) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.mStorage = mStorage;
        this.mViewType = mViewType;
    }

    public PostAdapter(Context mContext, List<String> mViewType) {
        this.mContext = mContext;
        this.mViewType = mViewType;
    }

    public PostAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setStorage(List<History> mStorage) {
        this.mStorage = mStorage;
    }

    public void setViewType(List<String> mViewType) {
        this.mViewType = mViewType;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
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
        return mViewType == null ? 0 : mViewType.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mViewType.get(position).equals("profile")){
            return PROFILE;
        } else if(mViewType.get(position).equals("food")){
            return FOOD;
        } else if(mViewType.get(position).equals("restaurant")){
            return RESTAURANT;
        } else if(mViewType.get(position).equals("all")){
            return ALL;
        }
        return -1;
    }

    private void configureProfileViewHolder(ProfileHolder profileHolder, int position){
        Glide.with(mContext).load(mUser.getImage()).into(profileHolder.iv_image);
        profileHolder.tv_name.setText(mUser.getName());
        profileHolder.tv_facebook.setText(mUser.getFacebook());
        profileHolder.tv_description.setText(mUser.getDescription());
    }

    private void configureFoodHistoryViewHolder(FoodHistoryHolder foodHistoryHolder, int position){
        Glide.with(mContext).load(mUser.getImage()).into(foodHistoryHolder.iv_userImage);
        Glide.with(mContext).load(mStorage.get(position).getFoodImage()).into(foodHistoryHolder.iv_foodImage);
        foodHistoryHolder.tv_cost.setText(String.valueOf(mStorage.get(position).getCost()));
        foodHistoryHolder.tv_foodName.setText(mStorage.get(position).getFoodName());
        foodHistoryHolder.tv_number_of_people.setText(String.valueOf(mStorage.get(position).getNumberOfPeople()));
        foodHistoryHolder.tv_username.setText(mUser.getName());
        foodHistoryHolder.tv_piece.setText(String.valueOf(mStorage.get(position).getPiece()));
        foodHistoryHolder.tv_topic.setText(mStorage.get(position).getHistoryName());
    }

    public void addData(History data) {
        this.mStorage.add(data);
        notifyItemInserted(this.mStorage.size() - 1);
    }

    public void addType(String type){
        this.mViewType.add(type);
    }

    public void addAll(List<String> viewType, List<History> storage) {
        for(String type : viewType){
            addType(type);
        }
        for (History data : storage) {
            addData(data);
        }
    }

    public void remove(Object data) {
        int position = mStorage.indexOf(data);
        if (position > -1) {
            mStorage.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

//    public void addLoadingFooter() {
//        isLoadingAdded = true;
//        add(new Movie());
//    }

//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//
//        int position = movies.size() - 1;
//        Movie item = getItem(position);
//
//        if (item != null) {
//            movies.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

    public Object getItem(int position) {
        return mStorage.get(position);
    }

}
