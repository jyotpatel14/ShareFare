package com.example.sharefare;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class QuoteAdapter extends FirebaseRecyclerAdapter<Quote, QuoteViewHolder> {

    public QuoteAdapter(@NonNull FirebaseRecyclerOptions<Quote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuoteViewHolder holder, int position, @NonNull Quote model) {
        holder.quoteTv.setText(model.getQuote());
        holder.authorTv.setText(model.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), UpdateQuoteActivity.class);
                intent.putExtra("key", model.getKey());
                intent.putExtra("quote", model.getQuote());
                intent.putExtra("author", model.getAuthor());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_item, parent, false);
        return new QuoteViewHolder(view);
    }
}
