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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText quote,author;
    private Button addQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_quote);

        //bind View
        quote = findViewById(R.id.quote);
        author = findViewById(R.id.author);
        addQuote = findViewById(R.id.addQuote);

        //add Listener
        addQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text
                String quoteStr = quote.getText().toString().trim();
                String authorStr = author.getText().toString().trim();
                // check If Empty
                if (quoteStr.isEmpty()){
                    quote.setError("Cannot be Empty");
                    return;
                }
                if (authorStr.isEmpty()){
                    author.setError("Cannot be Empty");
                    return;
                }
                //add to DB
                addQuoteToDB(quoteStr,authorStr);
            }
        });
        //Create Data
    }

    private void addQuoteToDB(String quoteStr, String authorStr){
        //create Hashmap
        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("quote",quoteStr);
        quoteHashmap.put("author",authorStr);

        //instantiate DB con
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference quotesRef = database.getReference("quotes");

        String key = quotesRef.push().getKey();
        quoteHashmap.put("key",key);

        //execute Write
        quotesRef.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddQuoteActivity.this,"Added",Toast.LENGTH_LONG).show();
                quote.getText().clear();
                author.getText().clear();
            }
        });


    }


}