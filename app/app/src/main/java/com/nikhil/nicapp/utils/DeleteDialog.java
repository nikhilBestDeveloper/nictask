package com.nikhil.nicapp.utils;

import android.app.AlertDialog;
import android.content.Context;

public class DeleteDialog {

    public interface DeleteDialogListener {
        void onDeleteConfirmed();
        void onCancelClicked();
    }

    public static void showDeleteDialog(Context context, DeleteDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            if (listener != null) {
                listener.onDeleteConfirmed();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onCancelClicked();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
