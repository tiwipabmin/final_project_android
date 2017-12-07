package com.tiwipabmin.eatallday.controller.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tiwipabmin.eatallday.controller.activity.RandomActivity;
import com.tiwipabmin.eatallday.model.Menu;
import com.tiwipabmin.eatallday.model.History;
import com.tiwipabmin.eatallday.api.HistoryApi;
import com.tiwipabmin.eatallday.controller.activity.TabBarActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tiwipabmin.eatallday.R;

public class AddTopicDialogFragment extends DialogFragment {

    @BindView(R.id.et_topics)
    EditText et_topics;

    private Menu mMenu;
    private Context mContext;
    private View mView;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mMenu = args.getParcelable("menu");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_topic, null);
        ButterKnife.bind(this, mView);

        alertDialog.setView(mView);

        return alertDialog.create();
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkTouched(){
        if(!et_topics.getText().toString().isEmpty()){

            String newKey = HistoryApi.getHistoryApi().getChildHistory().push().getKey();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.US);
            String date = fmt.format(new Date());

            History newHistory = new History(newKey, TabBarActivity.USER.getUserId(), mMenu.getName(), et_topics.getText().toString(), mMenu.getImage(), mMenu.getCost(), mMenu.getCurrency(), mMenu.getAmount(), mMenu.getUnit(), date, "food");
            HistoryApi.getHistoryApi().newHistory(getActivity(), newKey, newHistory);
            ((RandomActivity)mContext).finish();
            this.dismiss();
        }
    }

    public static AddTopicDialogFragment newInstance(Context context, Menu menu) {
        Bundle args = new Bundle();
        args.putParcelable("menu", menu);
        AddTopicDialogFragment fragment = new AddTopicDialogFragment();
        fragment.setContext(context);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this, mView).unbind();
    }
}
