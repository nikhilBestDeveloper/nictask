package com.nikhil.nicapp.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecorationHorizontal extends RecyclerView.ItemDecoration {
    private final int spacing;

    public SpaceItemDecorationHorizontal(Context context, int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Apply spacing to all sides of the item (top, bottom, left, right)
        outRect.top = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spacing;
        } else {
            outRect.left = 0;
        }
    }
}

