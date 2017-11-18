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
import kmitl.it13.millibear.eatallday.adapter.holder.FoodHolder;
import kmitl.it13.millibear.eatallday.controller.activity.FoodItemActivity;
import kmitl.it13.millibear.eatallday.controller.fragment.DetailFoodDialogFragment;
import kmitl.it13.millibear.eatallday.model.Food;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodHolder>{

    private Context context;
    private ArrayList<Food> mMenu;
    private FoodItemListener listener;

    public interface FoodItemListener {

        public void onBtnAddTouched(int position);
    }

    public FoodItemAdapter(Context context, ArrayList<Food> mMenu, FoodItemListener listener) {
        this.context = context;
        this.mMenu = mMenu;
        this.listener = listener;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_food, null);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodHolder holder, final int position) {
        Glide.with(context).load(mMenu.get(position).getImage()).into(holder.iv_food);
        holder.iv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment detailFoodDialogFragment = new DetailFoodDialogFragment().newInstance(mMenu.get(position));
                detailFoodDialogFragment.show(((FoodItemActivity)context).getSupportFragmentManager(), "detailFood");
            }
        });

        holder.tv_name.setText(mMenu.get(position).getName());
        holder.tv_cost.setText(String.valueOf(mMenu.get(position).getCost()).concat(" Baht / 1 Piece"));

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnAddTouched(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }

}
