package com.example.sharefare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateQuoteActivity extends AppCompatActivity {

    private EditText quote, author;
    private Button updateQuote, deleteQuote;
    private DatabaseReference quotesRef;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quote);

        // Bind View
        quote = findViewById(R.id.quote);
        author = findViewById(R.id.author);
        updateQuote = findViewById(R.id.updateQuote);
        deleteQuote = findViewById(R.id.deleteQuote);

        // Get data from intent
        key = getIntent().getStringExtra("key");
        String quoteStr = getIntent().getStringExtra("quote");
        String authorStr = getIntent().getStringExtra("author");

        // Set data to EditText
        quote.setText(quoteStr);
        author.setText(authorStr);

        // Firebase reference
        quotesRef = FirebaseDatabase.getInstance().getReference("quotes").child(key);

        // Update Listener
        updateQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuoteStr = quote.getText().toString().trim();
                String newAuthorStr = author.getText().toString().trim();

                if (newQuoteStr.isEmpty()) {
                    quote.setError("Cannot be empty");
                    return;
                }

                if (newAuthorStr.isEmpty()) {
                    author.setError("Cannot be empty");
                    return;
                }

                updateQuoteInDB(newQuoteStr, newAuthorStr);
            }
        });

        // Delete Listener
        deleteQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuoteInDB();
            }
        });
    }

    private void updateQuoteInDB(String quoteStr, String authorStr) {
        quotesRef.child("quote").setValue(quoteStr);
        quotesRef.child("author").setValue(authorStr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateQuoteActivity.this, "Quote updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UpdateQuoteActivity.this, "Failed to update quote", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteQuoteInDB() {
        quotesRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateQuoteActivity.this, "Quote deleted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UpdateQuoteActivity.this, "Failed to delete quote", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}