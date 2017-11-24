package kmitl.it13.millibear.eatallday.api;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void newHistory(Context context, String newKey, History newHistory) {
        childHistory.child(newKey).setValue(newHistory);
        Toast.makeText(context, "บันทึกประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

    public void deleteRandomMenuHistory(final Context context, final History history) {
        childHistory.child(history.getHisId()).removeValue();
        Toast.makeText(context, "ลบประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

}
