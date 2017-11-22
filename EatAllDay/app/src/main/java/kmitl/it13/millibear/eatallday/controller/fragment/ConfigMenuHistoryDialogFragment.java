package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.api.HistoryApi;
import kmitl.it13.millibear.eatallday.model.History;

public class ConfigMenuHistoryDialogFragment extends DialogFragment {

    private History mHistory;
    private String mImageMenuHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mHistory = args.getParcelable("history");
        mImageMenuHistory = args.getString("imageMenuHistory");
    }

    @OnClick(R.id.item_share_menu_history)
    public void onItemShareMenuHistoryTouched(){

        shareOnFacebook(mImageMenuHistory);
        dismiss();
    }

    @OnClick(R.id.item_delete_menu_history)
    public void onItemDeleteMenuHistoryTouched(){

        deleteMenu(mHistory);
        dismiss();
        Toast.makeText(getContext(), "ลบประวัติรายการอาหารเรียบร้อยแล้วขอรับ.", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_menu_history, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        return alertDialog.create();
    }

    private void shareOnFacebook(String path){
        File imageFile = new File(path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
        getActivity().startActivity(Intent.createChooser(intent, "Share to..."));
    }

    public static ConfigMenuHistoryDialogFragment newInstance(History history, String imageMenuHistory) {

        Bundle args = new Bundle();
        args.putParcelable("history", history);
        args.putString("imageMenuHistory", imageMenuHistory);
        ConfigMenuHistoryDialogFragment fragment = new ConfigMenuHistoryDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteMenu(History history){
        HistoryApi.getHistoryApi().getChildHistory().child(mHistory.getHisId()).removeValue();
    }
}
