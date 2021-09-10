package com.example.android.readit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {
    TextView titleTV, subTitleTV,descriptionTV,publisherTV,pagesTV,dateTV;
    Button preview,buy;
    String title, subtitle, publisher, publishedDate, description;
    String thumbnail ,previewLink ,buyLink,infoLink;
    ImageView bookIV;
    int pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        titleTV = findViewById(R.id.book_title);
        subTitleTV = findViewById(R.id.book_subtitle);
        descriptionTV = findViewById(R.id.book_description);
        publisherTV = findViewById(R.id.publisher);
        pagesTV = findViewById(R.id.page_count);
        dateTV = findViewById(R.id.date);
        preview = findViewById(R.id.preview_button);
        buy = findViewById(R.id.buy_button);
        bookIV = findViewById(R.id.book_page);

        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        publisher = getIntent().getStringExtra("publisher");
        publishedDate = getIntent().getStringExtra("publishedDate");
        description = getIntent().getStringExtra("description");
        pages = getIntent().getIntExtra("pageCount", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");
        buyLink = getIntent().getStringExtra("buyLink");

        // after getting the data we are setting
        // that data to our text views and image view.
        titleTV.setText(title);
        subTitleTV.setText(subtitle);
        publisherTV.setText(publisher);
        dateTV.setText("Published On : " + publishedDate);
        descriptionTV.setText(description);
        pagesTV.setText("No Of Pages : " + pages);
        if(thumbnail != null){
        thumbnail.replace("http","https");

        Picasso.get().load(thumbnail).into(bookIV);}

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(previewLink.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No Preview Link Found!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buyLink.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No Buy Link Found!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse(buyLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

    }
}