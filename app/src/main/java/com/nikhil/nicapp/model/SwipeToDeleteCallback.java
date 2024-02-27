package com.nikhil.nicapp.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private SwipeToDeleteListener listener;
    private Context context;
    private Paint paint;
    private Rect backgroundBounds;
    private int backgroundColor;
    private int deleteIcon;
    private int intrinsicWidth;
    private int intrinsicHeight;

    public SwipeToDeleteCallback(Context context, SwipeToDeleteListener listener) {
        super(0, ItemTouchHelper.LEFT);
        this.listener = listener;
        this.context = context;
        paint = new Paint();
        backgroundBounds = new Rect();
        backgroundColor = Color.RED;
        deleteIcon = android.R.drawable.ic_menu_delete; // You can replace this with your own delete icon
        intrinsicWidth = ContextCompat.getDrawable(context, deleteIcon).getIntrinsicWidth();
        intrinsicHeight = ContextCompat.getDrawable(context, deleteIcon).getIntrinsicHeight();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            listener.onSwipe(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        // Draw the red background
        paint.setColor(backgroundColor);
        backgroundBounds.set(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        canvas.drawRect(backgroundBounds, paint);

        // Calculate position of delete icon
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        // Draw the delete icon
        ContextCompat.getDrawable(context, deleteIcon).setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        ContextCompat.getDrawable(context, deleteIcon).draw(canvas);

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface SwipeToDeleteListener {
        void onSwipe(int position);
    }
}
