package com.example.abd_elmalek.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListener  {
public     String moviesJsonStr ;
    URL url;
   public GridView mymoviesGrid;
    ImageView imageView ;
   private MovieListener movieListener;
    boolean mIstwopane =false;
    public void setMovieListener (MovieListener listener){
        this.movieListener =listener;
    }
     ArrayList<MyMovies> myallMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMovieListener(this);

        if (null != findViewById(R.id.frsg_detail)){
               mIstwopane =true;
           }
        try {
            url = new URL("https://api.themoviedb.org/3/discover/movie?page=1&include_video=true&include_adult=false&sort_by=popularity.desc&language=en-US&api_key=09cbdff2f5305b3f61c87d12ddff0b6e");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        myallMovies = new ArrayList<>();
        imageView =(ImageView)findViewById(R.id.image_view);
        mymoviesGrid = (GridView) findViewById(R.id.gridview);
    try {
        GetMovies moviestask=new GetMovies();
        moviestask.execute();

    }catch (Exception e){
        Toast.makeText(this,"no intrenet connection",Toast.LENGTH_SHORT).show();
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.fav){

           myallMovies=FavoriteMovieDataSource.getInstance(getApplicationContext()).getFav();
            mymoviesGrid.setAdapter(new MyImageAdapter(myallMovies,getApplicationContext()));

        }

        if (id==R.id.mostpopular){
            myallMovies.clear();

            try {

                url = new URL("https://api.themoviedb.org/3/discover/movie?page=1&include_video=true&include_adult=false&sort_by=popularity.desc&language=en-US&api_key=09cbdff2f5305b3f61c87d12ddff0b6e");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetMovies mostpopularmoviestask=new GetMovies();
            mostpopularmoviestask.execute();


        } else if (id==R.id.rated){

            myallMovies.clear();
            try {
                url = new URL("https://api.themoviedb.org/3/discover/movie?page=1&include_video=true&include_adult=false&sort_by=vote_average.desc&language=en-US&api_key=09cbdff2f5305b3f61c87d12ddff0b6e");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetMovies highratedmoviestask=new GetMovies();
            highratedmoviestask.execute();
            MyImageAdapter imageAdapter =new MyImageAdapter(myallMovies,this);
            imageAdapter.notifyDataSetChanged();

        }
        else if (id==R.id.nowplaying){

            myallMovies.clear();
            try {
                url = new URL("https://api.themoviedb.org/3/movie/now_playing?api_key=09cbdff2f5305b3f61c87d12ddff0b6e&language=en-US&page=1");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetMovies nowplayingtask=new GetMovies();
            nowplayingtask.execute();
            MyImageAdapter imageAdapter =new MyImageAdapter(myallMovies,this);
            imageAdapter.notifyDataSetChanged();
        }
        else if (id==R.id.upcoming){

            myallMovies.clear();
            try {
                url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=09cbdff2f5305b3f61c87d12ddff0b6e&language=en-US&page=1");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetMovies upcomingtask=new GetMovies();
            upcomingtask.execute();
            MyImageAdapter imageAdapter =new MyImageAdapter(myallMovies,this);
            imageAdapter.notifyDataSetChanged();
        }
        else if (id==R.id.latest){

            myallMovies.clear();
            try {
                url = new URL("https://api.themoviedb.org/3/movie/latest?api_key=09cbdff2f5305b3f61c87d12ddff0b6e&language=en-US");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetMovies latesttask=new GetMovies();
            latesttask.execute();
            if (myallMovies.size()==0){Toast.makeText(this,"no results available",Toast.LENGTH_SHORT).show();}
            MyImageAdapter imageAdapter =new MyImageAdapter(myallMovies,this);
            imageAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedmovie(ArrayList<MyMovies> moviesArrayList, int position) {

        if (!mIstwopane){
            Intent l =new Intent(getApplicationContext(),DetailActivity.class);
                    l.putExtra("postion",position);
                  l.putExtra("mymovies",myallMovies);
                startActivity(l);
        } else {

            FragmentDetails fragmentDetails =new FragmentDetails();
            Bundle bundle =new Bundle();
            bundle.putSerializable("mymovies",myallMovies);
            bundle.putInt("postion",position);
            fragmentDetails.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frsg_detail,fragmentDetails,"").commit();

        }

    }
    public class GetMovies extends AsyncTask<Object, Object, String>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public String doInBackground(Object... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
       try {

           urlConnection = (HttpURLConnection) url.openConnection();
           urlConnection.setRequestMethod("GET");
           urlConnection.connect();

           InputStream inputStream = null;
           try {
               inputStream = urlConnection.getInputStream();
           } catch (IOException e) {
               e.printStackTrace();
           }

           StringBuffer buffer = new StringBuffer();
           if (inputStream == null) {
               // Nothing to do.
               return null;
           }
           reader = new BufferedReader(new InputStreamReader(inputStream));
           if (buffer.length() == 0) {
               // Stream was empty.  No point in parsing.return null;
           }
           String line;
           while ((line = reader.readLine()) != null) {
               buffer.append(line + "\n");
           }

           moviesJsonStr = buffer.toString();
           Log.i("movies",moviesJsonStr);


       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (urlConnection != null) {
               urlConnection.disconnect();
           }
           if (reader != null) {
               try {
                   reader.close();
               } catch (final IOException e) {
                   Log.e("error", "Error closing stream", e);
               }
           }
       }
          try {
              JSONObject jsonObject = null;
              try {
                  jsonObject = new JSONObject(moviesJsonStr);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              JSONArray moviesArray = null;
              try {
                  moviesArray = jsonObject.getJSONArray ("results");
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              Log.i("movies",moviesJsonStr);
              for (int i=0; i < moviesArray.length() ; i++ ){

                  JSONObject moviesJson = null;
                  try {
                      moviesJson = moviesArray.getJSONObject(i);


                      if(moviesJson.has("overview") && moviesJson.get("overview").toString() != null && !moviesJson.get("overview").toString().equals("null")
                              && moviesJson.has("release_date") && moviesJson.get("release_date").toString()!= null && !moviesJson.get("release_date").toString().equals("null")
                              && moviesJson.has("vote_average")&& moviesJson.get("vote_average").toString() != null && !moviesJson.get("vote_average").toString().equals("null")
                              && moviesJson.has("poster_path") &&moviesJson.get("poster_path").toString()!= null && !moviesJson.get("poster_path").toString().equals("null")
                              && moviesJson.has("title") &&moviesJson.get("title").toString() != null && !moviesJson.get("title").toString().equals("null")) {

                          myallMovies.add(new MyMovies (moviesJson.getString ("overview"),
                                  moviesJson.getString ("release_date"),moviesJson.getString("vote_average"), moviesJson.getString("poster_path"
                          ),moviesJson.getString ("title"),moviesJson.getString("id"),moviesJson.getString("backdrop_path") ));

                      }
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }


        }catch (Exception e){

              e.printStackTrace();

          } return moviesJsonStr ;}
        @Override
        protected void onPostExecute(String s) {
            mymoviesGrid.setAdapter(new MyImageAdapter(myallMovies,getApplicationContext()));
            mymoviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    movieListener.selectedmovie(myallMovies,position);

                }
            });
            super.onPostExecute(s);
        }
    }
}
