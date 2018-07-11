package com.daniel.test_reign.adapters.viewholder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daniel.test_reign.R;
import com.daniel.test_reign.adapters.HitsAdapter;
import com.daniel.test_reign.core.models.HitsObject;
import com.daniel.test_reign.utils.UserUtils;

public class HitsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtTitle;
    private TextView txtDescription;
    private LinearLayout lyCard;

    private HitsViewHolder(View parent, TextView txtTitle, TextView txtDescription, LinearLayout lyCard) {
        super(parent);
        this.txtTitle = txtTitle;
        this.txtDescription = txtDescription;
        this.lyCard = lyCard;
    }

    public static HitsViewHolder newInstance(View parent) {
        TextView txtTitle = parent.findViewById(R.id.txtTitle);
        TextView txtDescription = parent.findViewById(R.id.txtDescription);
        LinearLayout lyCard = parent.findViewById(R.id.lyCard);

        return new HitsViewHolder(parent, txtTitle, txtDescription, lyCard);
    }

    @SuppressLint("SetTextI18n")
    public void createViewAdapter(HitsViewHolder mHolder, final HitsObject item, final HitsAdapter.OnItemClickListener listener) {

        if (item.getStory_title() != null && !"".equalsIgnoreCase(item.getStory_title()))
            mHolder.txtTitle.setText(item.getStory_title());
        else if (item.getTitle() != null && !"".equalsIgnoreCase(item.getTitle()))
            mHolder.txtTitle.setText(item.getTitle());
        else
            mHolder.txtTitle.setText("");

        if (item.getAuthor() != null && !"".equalsIgnoreCase(item.getAuthor()))
            if (item.getCreated_at() != null && !"".equalsIgnoreCase(item.getCreated_at()))
                mHolder.txtDescription.setText(item.getAuthor() + " - " + UserUtils.convertTimeInMillis(item.getCreated_at()) + "h");
            else
                mHolder.txtDescription.setText(item.getAuthor());
        else
            mHolder.txtDescription.setText("");

        mHolder.lyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item, v);
            }
        });

    }
}
