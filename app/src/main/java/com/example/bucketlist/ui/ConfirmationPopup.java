package com.example.bucketlist.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bucketlist.R;


public class ConfirmationPopup {
    boolean flag;
    public boolean popup (Context mContext, ConstraintLayout mConstraintLayout, Activity mActivity) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        PopupWindow mPopupWindow;
        View customView = inflater.inflate(R.layout.confirmation_popup,null);
        mPopupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        Button closeButton = (Button) customView.findViewById(R.id.btnCancel);
        final PopupWindow finalMPopupWindow = mPopupWindow;
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalMPopupWindow.dismiss();
                flag = false;
            }
        });
        Button okButton = (Button) customView.findViewById(R.id.btnYes);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
            }
        });
        mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER,0,0);
        return flag;
    }
}
