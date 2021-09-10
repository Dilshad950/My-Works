package com.example.android.readit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private ArrayList<BookInfo> bookInfoArrayList ;
    public BookAdapter(Context context, ArrayList<BookInfo> bookInfoArrayList) {
        this.context = context;
        this.bookInfoArrayList = bookInfoArrayList;
    }


    public BookAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        return  new BookViewHolder(view);

    }
    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        final BookInfo mBookInfo = bookInfoArrayList.get(position);
        holder.nameTV.setText(mBookInfo.getTitle());
        holder.publisherTV.setText(mBookInfo.getPublisher());
        holder.pagesTV.setText("No of Pages: "+ mBookInfo.getPageCount());
        holder.dateTV.setText("Published On: " + mBookInfo.getPublishedDate());
        String thumbnail = mBookInfo.getThumbnail();

        if(thumbnail!=null){
            thumbnail.replace("http","https");
        Picasso.get().load(thumbnail).into(holder.bookIv);}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BookDetails.class);
                i.putExtra("title", mBookInfo.getTitle());
                i.putExtra("subtitle", mBookInfo.getSubtitle());
                i.putExtra("authors", mBookInfo.getAuthors());
                i.putExtra("publisher", mBookInfo.getPublisher());
                i.putExtra("publishedDate", mBookInfo.getPublishedDate());
                i.putExtra("description", mBookInfo.getDescription());
                i.putExtra("pageCount", mBookInfo.getPageCount());
                i.putExtra("thumbnail", mBookInfo.getThumbnail());
                i.putExtra("previewLink", mBookInfo.getPreviewLink());
                i.putExtra("infoLink", mBookInfo.getInfoLink());
                i.putExtra("buyLink", mBookInfo.getBuyLink());

                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {

        return bookInfoArrayList.size();
    }
    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV,publisherTV,pagesTV, dateTV;
        ImageView bookIv;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.book_title);
            publisherTV= itemView.findViewById(R.id.publisher);
            pagesTV= itemView.findViewById(R.id.page_count);
            dateTV = itemView.findViewById(R.id.date);
            bookIv = itemView.findViewById(R.id.book_page);
        }
    }
}
