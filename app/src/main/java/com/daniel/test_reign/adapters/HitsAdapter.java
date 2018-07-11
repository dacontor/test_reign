package com.daniel.test_reign.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.test_reign.R;
import com.daniel.test_reign.adapters.viewholder.HitsViewHolder;
import com.daniel.test_reign.core.models.HitsObject;

import java.util.List;

public class HitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HitsObject> mItems;
    private OnItemClickListener listener;

    public HitsAdapter(List<HitsObject> list, OnItemClickListener listener) {
        mItems = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_hits, viewGroup, false);
        return HitsViewHolder.newInstance(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        HitsObject object = getItem(position);
        HitsViewHolder holder = (HitsViewHolder) viewHolder;

        createViewAdapter(holder, object);

    }

    private void createViewAdapter(HitsViewHolder holder, HitsObject item) {
        holder.createViewAdapter(holder, item, listener);
    }

    private HitsObject getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(HitsObject item, View view);
    }

}
