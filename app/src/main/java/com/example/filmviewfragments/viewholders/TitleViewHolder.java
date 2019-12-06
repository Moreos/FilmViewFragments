package com.example.filmviewfragments.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.filmviewfragments.R;

public class TitleViewHolder extends RecyclerView.ViewHolder {
    private TextView itemTitle;

    public TitleViewHolder(@NonNull View itemView, ViewGroup parent) {
        super(itemView);


        RecyclerView.LayoutManager manager = ((RecyclerView) parent).getLayoutManager();

        if (manager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()).setFullSpan(true);

//            Toast.makeText(mainContext, "DA", Toast.LENGTH_SHORT).show();
//            (((StaggeredGridLayoutManager)manager).LayoutParams);
        }

        itemTitle = itemView.findViewById(R.id.itemTitle);
    }

    public void setTitle(String title) {
        itemTitle.setText(title);
    }
}
