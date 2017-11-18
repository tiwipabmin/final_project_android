package kmitl.it13.millibear.eatallday.controller.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kmitl.it13.millibear.eatallday.R;

public class CaptureDialogFragment extends DialogFragment {

    @BindView(R.id.iv_captureImage)
    ImageView iv_image;

    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.capture_image, null);
        ButterKnife.bind(this, view);
        Toast.makeText(getActivity(), bitmap + "", Toast.LENGTH_SHORT).show();
        iv_image.setImageBitmap(bitmap);

        return alertDialog.create();
    }

    public static CaptureDialogFragment newInstance(Bitmap bitmap) {
        Bundle args = new Bundle();
        CaptureDialogFragment fragment = new CaptureDialogFragment();
        fragment.setBitmap(bitmap);
        fragment.setArguments(args);
        return fragment;
    }
}
