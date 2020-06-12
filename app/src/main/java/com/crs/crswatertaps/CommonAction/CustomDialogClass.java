package com.crs.crswatertaps.CommonAction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.crs.crswatertaps.R;

public class CustomDialogClass  {

    public interface WarningResponse{
        public void onPositive();
        public void onNegative();
    }
    public static void showWarning(Context c, String message, String positiveBtnText, final WarningResponse responseListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        View v = View.inflate(c, R.layout.dailog_loyout, null);
        builder.setView(v);
        ((TextView) v.findViewById(R.id.message)).setText(message);

        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (responseListener != null) {
                    responseListener.onPositive();
                }
            }
        });
        final AlertDialog dialog =  builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
    public static void showDialog(Context c,String message,String positiveBtnText,String negativeBtnText,final WarningResponse responseListener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        View v = View.inflate(c, R.layout.dailog_loyout, null);
        builder.setView(v);
        ((TextView) v.findViewById(R.id.message)).setText(message);

        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (responseListener != null) {
                    responseListener.onPositive();
                }
            }
        });
        builder.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (responseListener != null) {
                    responseListener.onNegative();
                }
            }
        });
        final AlertDialog dialog =  builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

}
