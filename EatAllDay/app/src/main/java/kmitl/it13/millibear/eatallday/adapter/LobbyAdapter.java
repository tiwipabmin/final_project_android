package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.SaveImage;
import kmitl.it13.millibear.eatallday.adapter.holder.RandomMenuHistoryViewHolder;
import kmitl.it13.millibear.eatallday.adapter.holder.ProfileViewHolder;
import kmitl.it13.millibear.eatallday.api.UserApi;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;
import kmitl.it13.millibear.eatallday.controller.fragment.ConfigMenuHistoryDialogFragment;
import kmitl.it13.millibear.eatallday.model.History;
import kmitl.it13.millibear.eatallday.model.User;

public class LobbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PROFILE = 0, FOOD = 1;

    private Context mContext;
    private User mUser;
    private List<Object> mStorage;
    private List<String> mViewType;

    public LobbyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setStorage(List<Object> mStorage) {
        this.mStorage = mStorage;
    }

    public void setViewType(List<String> mViewType) {
        this.mViewType = mViewType;
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

    private void configureProfileViewHolder(final ProfileViewHolder profileViewHolder, int position){
        mUser = (User) mStorage.get(position);
        Glide.with(mContext).load(mUser.getImage()).into(profileViewHolder.iv_image);
        profileViewHolder.tv_name.setText(mUser.getName());
        profileViewHolder.tv_facebook.setText(mUser.getFacebook());
        profileViewHolder.tv_description.setText(mUser.getDescription());

        profileViewHolder.iv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvEditProfileTouched(profileViewHolder, mUser);
            }
        });
    }

    private void onIvEditProfileTouched(final ProfileViewHolder profileViewHolder, final User user){

        profileViewHolder.tv_name.setVisibility(View.GONE);
        profileViewHolder.tv_description.setVisibility(View.GONE);
        profileViewHolder.tv_facebook.setVisibility(View.GONE);
        profileViewHolder.iv_edit_profile.setVisibility(View.GONE);

        profileViewHolder.et_name.setVisibility(View.VISIBLE);
        profileViewHolder.et_description.setVisibility(View.VISIBLE);
        profileViewHolder.et_facebook.setVisibility(View.VISIBLE);
        profileViewHolder.iv_verify.setVisibility(View.VISIBLE);

        profileViewHolder.et_name.setText(user.getName());
        profileViewHolder.et_description.setText(user.getDescription());
        profileViewHolder.et_facebook.setText(user.getFacebook());

        profileViewHolder.iv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(profileViewHolder.et_name.getText().toString());
                user.setFacebook(profileViewHolder.et_facebook.getText().toString());
                user.setDescription(profileViewHolder.et_description.getText().toString());
                UserApi.getUserApi().updateUser(user);

                profileViewHolder.tv_name.setVisibility(View.VISIBLE);
                profileViewHolder.tv_description.setVisibility(View.VISIBLE);
                profileViewHolder.tv_facebook.setVisibility(View.VISIBLE);
                profileViewHolder.iv_edit_profile.setVisibility(View.VISIBLE);

                profileViewHolder.et_name.setVisibility(View.GONE);
                profileViewHolder.et_description.setVisibility(View.GONE);
                profileViewHolder.et_facebook.setVisibility(View.GONE);
                profileViewHolder.iv_verify.setVisibility(View.GONE);

                Toast.makeText(mContext, "แก้ไขประวัติเรียบร้อย.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configureFoodHistoryViewHolder(final RandomMenuHistoryViewHolder randomMenuHistoryViewHolder, final int position){
        final History menuHistory = (History) mStorage.get(position);
        Glide.with(mContext).load(mUser.getImage()).into(randomMenuHistoryViewHolder.iv_userImage);
        Glide.with(mContext).load(menuHistory.getFoodImage()).into(randomMenuHistoryViewHolder.iv_foodImage);
        randomMenuHistoryViewHolder.tv_cost.setText(String.valueOf(menuHistory.getCost()).concat(" " + menuHistory.getCurrency()));
        randomMenuHistoryViewHolder.tv_foodName.setText(menuHistory.getFoodName());
        randomMenuHistoryViewHolder.tv_username.setText(mUser.getName());
        randomMenuHistoryViewHolder.tv_piece.setText(String.valueOf(menuHistory.getAmount()).concat(" " + menuHistory.getUnit()));
        randomMenuHistoryViewHolder.tv_topic.setText(menuHistory.getHistoryName());
        randomMenuHistoryViewHolder.tv_date.setText(menuHistory.getDate());

        randomMenuHistoryViewHolder.iv_config.setOnClickListener(new View.OnClickListener() {
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
                        ConfigMenuHistoryDialogFragment configMenuHistoryDialogFragment = new ConfigMenuHistoryDialogFragment().newInstance(menuHistory, path);
                        configMenuHistoryDialogFragment.show(((TabBarActivity)mContext).getSupportFragmentManager(), "configMenuHistoryDialog");
                    }
                });
            }
        });
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
