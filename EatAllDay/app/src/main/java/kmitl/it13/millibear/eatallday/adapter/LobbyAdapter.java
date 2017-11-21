package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SaveImage;
import kmitl.it13.millibear.eatallday.adapter.holder.RandomMenuHistoryViewHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.ProfileViewHolder;
import kmitl.it13.millibear.eatallday.model.History;
import kmitl.it13.millibear.eatallday.model.User;

public class LobbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PROFILE = 0, FOOD = 1;

    private Context mContext;
    private User mUser;
    private List<History> mStorage;
    private List<String> mViewType;

    public LobbyAdapter(Context mContext, User mUser, List<History> mStorage, List<String> mViewType) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.mStorage = mStorage;
        this.mViewType = mViewType;
    }

    public LobbyAdapter(Context mContext, List<String> mViewType) {
        this.mContext = mContext;
        this.mViewType = mViewType;
    }

    public LobbyAdapter(Context mContext) {
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
                viewHolder = new ProfileViewHolder(itemView1);
                break;
            case FOOD:
                View itemView2 = inflater.inflate(R.layout.item_random_menu_history, null);
                viewHolder = new RandomMenuHistoryViewHolder(itemView2);
                break;
            default:
                View itemView = inflater.inflate(R.layout.item_random_menu_history, null);
                viewHolder = new RandomMenuHistoryViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("Lifecycle", "onBindViewHolder");

        switch (holder.getItemViewType()){
            case PROFILE:
                ProfileViewHolder profileViewHolder = (ProfileViewHolder) holder;
                configureProfileViewHolder(profileViewHolder, position);
                break;
            case FOOD:
                RandomMenuHistoryViewHolder randomMenuHistoryViewHolder = (RandomMenuHistoryViewHolder) holder;
                configureFoodHistoryViewHolder(randomMenuHistoryViewHolder, position);
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
        }
        return -1;
    }

    private void configureProfileViewHolder(ProfileViewHolder profileViewHolder, int position){
        Glide.with(mContext).load(mUser.getImage()).into(profileViewHolder.iv_image);
        profileViewHolder.tv_name.setText(mUser.getName());
        profileViewHolder.tv_facebook.setText(mUser.getFacebook());
        profileViewHolder.tv_description.setText(mUser.getDescription());
    }

    private void configureFoodHistoryViewHolder(final RandomMenuHistoryViewHolder randomMenuHistoryViewHolder, int position){
        Glide.with(mContext).load(mUser.getImage()).into(randomMenuHistoryViewHolder.iv_userImage);
        Glide.with(mContext).load(mStorage.get(position).getFoodImage()).into(randomMenuHistoryViewHolder.iv_foodImage);
        randomMenuHistoryViewHolder.tv_cost.setText(String.valueOf(mStorage.get(position).getCost()));
        randomMenuHistoryViewHolder.tv_foodName.setText(mStorage.get(position).getFoodName());
        randomMenuHistoryViewHolder.tv_username.setText(mUser.getName());
        randomMenuHistoryViewHolder.tv_piece.setText(String.valueOf(mStorage.get(position).getPiece()));
        randomMenuHistoryViewHolder.tv_topic.setText(mStorage.get(position).getHistoryName());

        randomMenuHistoryViewHolder.iv_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomMenuHistoryViewHolder.layout.post(new Runnable() {
                    @Override
                    public void run() {
                        randomMenuHistoryViewHolder.layout.setDrawingCacheEnabled(true);
                        Bitmap bmp = Bitmap.createBitmap(randomMenuHistoryViewHolder.layout.getDrawingCache());
                        randomMenuHistoryViewHolder.layout.setDrawingCacheEnabled(false);
                        SaveImage saveImage = new SaveImage(mContext);
                        String path = saveImage.addImageToGallery(bmp);
                        shareOnFacebook(path);
                    }
                });
            }
        });
    }

    private void shareOnFacebook(String path){
        File imageFile = new File(path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
        mContext.startActivity(Intent.createChooser(intent, "Share to..."));
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
