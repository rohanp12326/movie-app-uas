package com.rohan.patil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.rohan.patil.adapter.MovieAdapter;
import com.rohan.patil.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //api=https://api.themoviedb.org/3/movie/popular?api_key=fed3065d290f59dee0b0433bc6ee9e39

    RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyecler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        requestQueue = VolleySingleton.getmInstance(getApplicationContext()).getRequestQueue();
        movieList = new ArrayList<>();
        fetchMovies();
    }

    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=fed3065d290f59dee0b0433bc6ee9e39";
        String baseposter = "https://themoviedb.org/t/p/w500";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String title = jsonObject1.getString("title");
                    String overview = jsonObject1.getString("overview");
                    String poster = baseposter + jsonObject1.getString("poster_path");
                    String release = jsonObject1.getString("release_date");

                    Movie movie = new Movie(title , poster , overview , release);
                    movieList.add(movie);
                }
                MovieAdapter adapter = new MovieAdapter(getApplicationContext() , movieList);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(stringRequest);
    }
}