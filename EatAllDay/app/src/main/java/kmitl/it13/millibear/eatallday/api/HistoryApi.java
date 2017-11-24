package kmitl.it13.millibear.eatallday.api;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kmitl.it13.millibear.eatallday.model.History;

public class HistoryApi {

    private static HistoryApi historyApi;
    private DatabaseReference childHistory;

    private HistoryApi() {

        childHistory = FirebaseDatabase.getInstance().getReference().child("history");
    }

    public static HistoryApi getHistoryApi() {
        if (historyApi == null) {
            historyApi = new HistoryApi();
        }
        return historyApi;
    }

    public DatabaseReference getChildHistory() {
        return childHistory;
    }

    public void newHistory(String newKey, History newHistory, final DialogFragment progress) {
        childHistory.child(newKey).setValue(newHistory);
        Toast.makeText(progress.getContext(), "บันทึกประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
        progress.dismiss();
    }

    public void deleteRandomMenuHistory(History history, final DialogFragment progress) {
        childHistory.child(history.getHisId()).removeValue();
        Toast.makeText(progress.getContext(), "ลบประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
        progress.dismiss();
    }

}
