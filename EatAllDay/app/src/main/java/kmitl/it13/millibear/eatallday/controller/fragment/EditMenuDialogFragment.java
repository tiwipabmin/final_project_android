package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.FoodApi;
import kmitl.it13.millibear.eatallday.controller.activity.EditMenuActivity;
import kmitl.it13.millibear.eatallday.model.Food;

public class EditMenuDialogFragment extends DialogFragment {

    private Food mMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mMenu = args.getParcelable("food");
    }

    @OnClick(R.id.item_edit_menu)
    public void onItemEditMenuTouched(){

        Intent intent = new Intent(getActivity(), EditMenuActivity.class);
        intent.putExtra("menu", mMenu);
        startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.item_delete_menu)
    public void onItemDeleteMenuTouched(){

        deleteMenu(mMenu);
        dismiss();
        Toast.makeText(getContext(), "ลบรายการอาหารเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_menu, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        return alertDialog.create();
    }

    public static EditMenuDialogFragment newInstance(Food food) {

        Bundle args = new Bundle();
        args.putParcelable("food", food);
        EditMenuDialogFragment fragment = new EditMenuDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteMenu(Food food){
        FoodApi.getFoodApi().getChildFood().child(food.getId()).removeValue();
    }
}