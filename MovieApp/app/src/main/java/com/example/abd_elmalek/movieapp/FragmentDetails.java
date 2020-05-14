package com.example.abd_elmalek.movieapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FragmentDetails extends Fragment implements Serializable {
    TextView title,rating,overview,releaasedate;
    ImageView imageView,backpost;
    ImageButton myfavoritebutton;
    ListView listView;
    ListView reviewlist;
    public String videosjsonstrng;
    String revjsonstrng;
    ArrayList revlist;
    URL url;
    ArrayList<MyMovies> mymovies;
    ArrayList<MyVideos> myVideoses;
    int position;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.detail_fragment, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      int id =item.getItemId();
        if (id==R.id.share_action){
            try {
                Intent myshareIntent = new Intent(Intent.ACTION_SEND);
                myshareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                myshareIntent.setType("text/plain");
                String url ="http://www.youtube.com/watch?v="+myVideoses.get(0).getVideokey();
                myshareIntent.putExtra(Intent.EXTRA_TEXT,url);
                startActivity(Intent.createChooser(myshareIntent, "Choose Action"));

            }catch (Exception e){Toast.makeText(getActivity(),"no trailers availabel to share",Toast.LENGTH_SHORT).show();}

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_details, container, false);
          setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        mymovies = (ArrayList<MyMovies>) bundle.getSerializable("mymovies");
        position = bundle.getInt("postion");
        title=(TextView)rootView.findViewById(R.id.textView);
        rating=(TextView)rootView.findViewById(R.id.textView2);
        releaasedate=(TextView)rootView.findViewById(R.id.textView4);
        overview=(TextView)rootView.findViewById(R.id.textView5);
        imageView=(ImageView)rootView.findViewById(R.id.detailimage);
        backpost=(ImageView)rootView.findViewById(R.id.backpostimage);
        listView=(ListView)rootView.findViewById(R.id.vidlist);
        reviewlist= (ListView)rootView.findViewById(R.id.myreviewlist);
        myVideoses= new ArrayList<>();
        revlist =new ArrayList();
        myVideoses.clear();
        VideosListAdapter videosListAdapter=new VideosListAdapter(getContext(),myVideoses);
        listView.setAdapter(videosListAdapter);
        MyReviewListAdapter myReviewListAdapter =new MyReviewListAdapter(revlist,getContext());
        reviewlist.setAdapter(myReviewListAdapter);

        try {
            final Getvideos getvideos =new Getvideos();
            getvideos.execute();
            final GetReviews getReviews =new GetReviews();
            getReviews.execute();

        }catch (Exception e){
            Toast.makeText(getActivity(),"no intrenet connection",Toast.LENGTH_SHORT).show();
        }

        myfavoritebutton =(ImageButton)rootView.findViewById(R.id.imageButton);
        if (FavoriteMovieDataSource.getInstance(getContext()).CheckIsFavforitebyPostpath(mymovies,position)){myfavoritebutton.setImageResource(R.drawable.favorite);}
        else {myfavoritebutton.setImageResource(R.drawable.unfavorite);}


        myfavoritebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FavoriteMovieDataSource.getInstance(getContext()).CheckIsFavforitebyPostpath(mymovies,position)){
                 try {myfavoritebutton.setImageResource(R.drawable.unfavorite);

                     FavoriteMovieDataSource.getInstance(getContext()).markAsFav(mymovies.get(position));
                     FavoriteMovieDataSource.getInstance(getContext()).deleteMovieswithTitle(mymovies.get(position).getTitle());
                 }catch (Exception e){
                     Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                 }

                } else {
                    myfavoritebutton.setImageResource(R.drawable.favorite);
                       FavoriteMovieDataSource.getInstance(getContext()).insertfavMovielist(mymovies.get(position));
                }
            }
        });

        title.setText("Title : "+mymovies.get(position).getTitle());
        title.setTextSize(17);
        rating.setText("Rating : "+mymovies.get(position).getReating());
        rating.setTextSize(17);
        releaasedate.setText("Release Date : "+mymovies.get(position).getReleasedate());
        releaasedate.setTextSize(17);
        overview.setText("OverView : "+mymovies.get(position).getOverview()+"\n");
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//"+mymovies.get(position).getPostpath()).fit().centerCrop().error(R.drawable.placeholder2).into((imageView));
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500//"+mymovies.get(position).getBackpost()).fit().centerCrop().error(R.drawable.placeholder2).into((backpost));
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mymovies.get(position).getTitle());

        return rootView;
    }

    public class Getvideos extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                url = new URL("http://api.themoviedb.org/3/movie/"+mymovies.get(position).getId()+"/videos?api_key=09cbdff2f5305b3f61c87d12ddff0b6e");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

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

                videosjsonstrng = buffer.toString();
                Log.i("movies",videosjsonstrng);

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
                    jsonObject = new JSONObject(videosjsonstrng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray moviesArray = null;
                try {
                    moviesArray = jsonObject.getJSONArray ("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("movies",videosjsonstrng);
                for (int i=0; i < moviesArray.length() ; i++ ){

                    JSONObject videoJson = null;
                    try {
                        videoJson = moviesArray.getJSONObject(i);
                        MyVideos videos = new MyVideos(videoJson.getString("name"),videoJson.getString("id"),videoJson.getString("key"));
                         myVideoses.add(videos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }catch (Exception e){

                e.printStackTrace();

            } return videosjsonstrng ;
        }

        @Override
        protected void onPostExecute(Object o) {

            VideosListAdapter videosListAdapter=new VideosListAdapter(getContext(),myVideoses);
            listView.setAdapter(videosListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent youtubeappIntent =new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube;"+myVideoses.get(position).getVideokey()));
                    Intent browserIntent =new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+myVideoses.get(position).getVideokey()));
                    try {
                            startActivity(youtubeappIntent);
                    }catch (ActivityNotFoundException e){
                        startActivity(browserIntent);
                    }
                }
            });
            super.onPostExecute(o);
        }

    }
    public class GetReviews extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {

                url = new URL("http://api.themoviedb.org/3/movie/" + mymovies.get(position).getId() +"/reviews?api_key=09cbdff2f5305b3f61c87d12ddff0b6e");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

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

                revjsonstrng = buffer.toString();

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
                    jsonObject = new JSONObject(revjsonstrng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray moviesArray = null;
                try {
                    moviesArray = jsonObject.getJSONArray ("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("movies",revjsonstrng);
                for (int i=0; i < moviesArray.length() ; i++ ){

                    JSONObject reviewJson = null;
                    try {
                        reviewJson = moviesArray.getJSONObject(i);
                        revlist.add(new MyReviews(reviewJson.getString("author"),reviewJson.getString("content")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }catch (Exception e){

                e.printStackTrace();

            } return revjsonstrng ;        }

        @Override
        protected void onPostExecute(Object o) {
            MyReviewListAdapter myReviewListAdapter =new MyReviewListAdapter(revlist,getContext());
            reviewlist.setAdapter(myReviewListAdapter);

            super.onPostExecute(o);
        }
    }
}
