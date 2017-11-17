package kmitl.it13.millibear.eatallday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.adapter.holder.FoodHolder;
import kmitl.it13.millibear.eatallday.model.Food;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodHolder>{

    private Context context;
    private ArrayList<Food> mMenu;

    public FoodItemAdapter(Context context, ArrayList<Food> mMenu) {
        this.context = context;
        this.mMenu = mMenu;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_food, null);
        return new FoodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodHolder holder, int position) {
        Glide.with(context).load(mMenu.get(position).getImage()).into(holder.iv_food);
        holder.tv_name.setText(mMenu.get(position).getName());
        holder.tv_cost.setText(String.valueOf(mMenu.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }
}
