package com.example.android.readit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity   {
    private ArrayList<BookInfo> bookInfoArrayList;
//    private EditText searchContent = findViewById(R.id.editText_search);
private EditText searchContent;
//    String url = "https://www.googleapis.com/books/v1/volumes?q=";
    private ImageButton searchButton;
    private ProgressBar progressBar;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView emptyTextView ;
    private ImageView empty_books;
//    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static final String LOG_TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookInfoArrayList = new ArrayList<>();
        mAdapter = new BookAdapter(MainActivity.this, bookInfoArrayList);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        searchContent = findViewById(R.id.editText_search);
        searchButton = findViewById(R.id.search_button);
        progressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view);


        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        emptyTextView = findViewById(R.id.empty_view);
        empty_books = findViewById(R.id.empty_books);
        emptyTextView.setText(R.string.default_empty_text);
        emptyTextView.setVisibility(View.VISIBLE);
        empty_books.setVisibility(View.VISIBLE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (searchContent.getText().toString().isEmpty()) {
                    searchContent.setError("Please Search Again");
                    Toast.makeText(MainActivity.this, "Please Search Again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                BookAsyncTask task = new  BookAsyncTask();
                task.execute();
                emptyTextView.setVisibility(View.GONE);
                bookInfoArrayList.clear();
                empty_books.setVisibility(View.GONE);


            }
        });
    }


    private void fetchBookInfo(String query) {
//        bookInfoArrayList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.getCache().clear();

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        url.replace(" ", "");

//        if (URLUtil.isValidUrl(url)) {
            RequestQueue newRequestQueue = Volley.newRequestQueue(MainActivity.this);

            JsonObjectRequest bookObjectRequest = new JsonObjectRequest(Request.Method.GET, url.trim(),null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray booksArray = response.getJSONArray("items");
                        for (int i = 0; i < booksArray.length(); i++) {
                            JSONObject itemsObj = booksArray.getJSONObject(i);
                            JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                            String title = volumeObj.optString("title");
                            String subtitle = volumeObj.optString("subtitle");
                            JSONArray authorsArray = volumeObj.getJSONArray("authors");
                            String publisher = volumeObj.optString("publisher");
                            String publishedDate = volumeObj.optString("publishedDate");
                            String description = volumeObj.optString("description");
                            int pageCount = volumeObj.optInt("pageCount");
                            JSONObject imageLinks = volumeObj.getJSONObject("imageLinks");
                            Log.v(LOG_TAG, "trying to get thumbnail");
                            String thumbnail = imageLinks.getString("thumbnail");
                            Log.v(LOG_TAG, "thumbnail obtained");
                            String previewLink = volumeObj.optString("previewLink");
                            String infoLink = volumeObj.optString("infoLink");
                            JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                            String buyLink = saleInfoObj.optString("buyLink");
                            ArrayList<String> authorsArrayList = new ArrayList<>();
                            if (authorsArray.length() != 0) {
                                for (int j = 0; j < authorsArray.length(); j++) {
                                    authorsArrayList.add(authorsArray.optString(i));
                                }
                            }

                            BookInfo bookInfo = new BookInfo(title, subtitle, description, infoLink, previewLink, authorsArrayList, publisher, publishedDate, pageCount, thumbnail, buyLink);
                            bookInfoArrayList.add(bookInfo);
                            BookAdapter bookAdapter = new BookAdapter(MainActivity.this, bookInfoArrayList);
                            linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                            mRecyclerView = findViewById(R.id.recycler_view);
                            mRecyclerView.setLayoutManager(linearLayoutManager);
//                        mRecyclerView.setAdapter(null);
                            mRecyclerView.setAdapter(bookAdapter);
                            Log.v("Adapter", "Adapter attached");
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    if(bookInfoArrayList.size()==0){
                        Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        empty_books.setVisibility(View.VISIBLE);
                        emptyTextView.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                }

            });
            newRequestQueue.add(bookObjectRequest);
        }
//        else{
//            Toast.makeText(MainActivity.this, "Please Spell Correctly", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.GONE);
//        }
//    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private class BookAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            fetchBookInfo(searchContent.getText().toString().replace(" ",""));
            return null;
        }
    }
}

