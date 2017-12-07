package com.tiwipabmin.eatallday.api;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiwipabmin.eatallday.model.History;

public class HistoryApi {

    private static HistoryApi mHistoryApi;
    private DatabaseReference mChildHistory;

    private HistoryApi() {

        mChildHistory = FirebaseDatabase.getInstance().getReference().child("history");
    }

    public static HistoryApi getHistoryApi() {
        if (mHistoryApi == null) {
            mHistoryApi = new HistoryApi();
        }
        return mHistoryApi;
    }

    public DatabaseReference getChildHistory() {
        return mChildHistory;
    }

    public void newHistory(Context context, String newKey, History newHistory) {
        mChildHistory.child(newKey).setValue(newHistory);
        Toast.makeText(context, "บันทึกประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

    public void deleteRandomMenuHistory(final Context context, final History history) {
        mChildHistory.child(history.getHisId()).removeValue();
        Toast.makeText(context, "ลบประวัติเรียบร้อยแล้วจ้า.", Toast.LENGTH_SHORT).show();
    }

}
