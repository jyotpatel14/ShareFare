package com.example.sharefare;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuoteViewHolder extends RecyclerView.ViewHolder {

    public TextView quoteTv,authorTv;
    public QuoteViewHolder(@NonNull View itemView) {
        super(itemView);
        quoteTv= itemView.findViewById(R.id.quote_tv);
        authorTv = itemView.findViewById(R.id.author_tv);
    }
}
