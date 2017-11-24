package kmitl.it13.millibear.eatallday.controller.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import kmitl.it13.millibear.eatallday.R;
import kmitl.it13.millibear.eatallday.controller.activity.TabBarActivity;

public class ProgressDialogFragment extends DialogFragment {

    private ImageView download;
    private Animation animLoading;

    public ProgressDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_progress, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){}
                return true;
            }
        });

        animLoading = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

        download = rootView.findViewById(R.id.loading);
        download.setAnimation(animLoading);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        download.clearAnimation();
    }


}
