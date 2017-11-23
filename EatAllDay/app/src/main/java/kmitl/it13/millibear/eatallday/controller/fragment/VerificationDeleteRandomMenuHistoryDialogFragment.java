package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import kmitl.it13.millibear.eatallday.api.HistoryApi;
import kmitl.it13.millibear.eatallday.model.History;

public class VerificationDeleteRandomMenuHistoryDialogFragment extends DialogFragment {

    private History mHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mHistory = args.getParcelable("history");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("คุณต้องการลบประวัติรายการอาหารมื้อนี้ใช่ไหม?")
                .setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("ใช่, ฉันต้องการลบ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRandomMenuHistory(mHistory);
                    }
                });
        return alertDialog.create();
    }

    public static VerificationDeleteRandomMenuHistoryDialogFragment newInstance(History history) {

        Bundle args = new Bundle();
        args.putParcelable("history", history);
        VerificationDeleteRandomMenuHistoryDialogFragment fragment = new VerificationDeleteRandomMenuHistoryDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteRandomMenuHistory(History history){
        HistoryApi.getHistoryApi().getChildHistory().child(history.getHisId()).removeValue();
    }
}
