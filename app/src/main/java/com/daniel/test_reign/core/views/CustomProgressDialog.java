package com.daniel.test_reign.core.views;

import android.app.Dialog;
import android.content.Context;

import com.daniel.test_reign.R;

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context mContext) {

        super(mContext, R.style.FullScreenDialogProgress);

        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setContentView(R.layout.progress_dialog);

    }

}
