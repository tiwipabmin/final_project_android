package kmitl.it13.millibear.eatallday.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import kmitl.it13.millibear.eatallday.R;

public class TypeElementDialogFragment extends DialogFragment {

    public interface TypeElementDialogFragmentListener {

        public void onTypeFoodTouched();

        public void onTypeRestaurantTouched();
    }

    private TypeElementDialogFragmentListener listener;

    public void setListener(TypeElementDialogFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder typeElementDialog = new AlertDialog.Builder(getActivity());
        typeElementDialog.setTitle(R.string.title_dialog_type_element)
                .setItems(R.array.items_type_element, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0: listener.onTypeFoodTouched();
                            break;
                            case 1: listener.onTypeRestaurantTouched();
                            break;
                        }
                    }
                });
        return typeElementDialog.create();
    }
}
