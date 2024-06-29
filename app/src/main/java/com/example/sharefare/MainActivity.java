package com.example.sharefare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private QuoteAdapter quoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.quotes_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Firebase reference
        DatabaseReference quotesRef = FirebaseDatabase.getInstance().getReference().child("quotes");


        //setup firebase
        FirebaseRecyclerOptions<Quote> options =
                new FirebaseRecyclerOptions.Builder<Quote>()
                        .setQuery(quotesRef, Quote.class)
                        .build();

        // Set up adapter
        quoteAdapter = new QuoteAdapter(options);
        recyclerView.setAdapter(quoteAdapter);


        //floating button action listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,AddQuoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        quoteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        quoteAdapter.stopListening();
    }
}