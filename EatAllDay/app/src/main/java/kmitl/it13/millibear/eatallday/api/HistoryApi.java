package kmitl.it13.millibear.eatallday.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tiwip on 11/5/2017.
 */

public class HistoryApi {

    private static HistoryApi historyApi;
    private DatabaseReference childHistory;

    private HistoryApi() {

        childHistory = FirebaseDatabase.getInstance().getReference().child("history");
    }

    public static HistoryApi getHistoryApi(){
        if(historyApi == null){
            historyApi = new HistoryApi();
        }
        return historyApi;
    }

    public DatabaseReference getChildHistory() {
        return childHistory;
    }
}
