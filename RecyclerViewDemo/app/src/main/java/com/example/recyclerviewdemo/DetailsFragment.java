package com.example.recyclerviewdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import static com.example.recyclerviewdemo.MainActivity.getBitmapFromURL;
import static com.example.recyclerviewdemo.UserRecyclerViewAdapter.model;

public class DetailsFragment extends Fragment {
    public static String Tag = "DeatilsFragment";
    ImageView img;
    TextView title, description;
    CardView cardView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment,container,false);
        initView(view);
        if (getResources().getBoolean(R.bool.twoPane)){
            model.getCake().observe(requireActivity(), (user)->{
                title.setText(user.getName());
                description.setText(user.getMobile());
                new ImageDownloader().execute(user.getProfile_imge());
            });
        }else {


        }
        model.getCake().observe(requireActivity(), (user)->{
            title.setText(user.getName());
            description.setText(user.getMobile());
            new ImageDownloader().execute(user.getProfile_imge());
        });
        return view;
    }

    private void initView(View view) {
        img = view.findViewById(R.id.cakeImg);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        cardView = view.findViewById(R.id.myCard);

    }

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap b = getBitmapFromURL(strings[0]);
            return b;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //Toast.makeText(MainActivity.this,"image downloaded successfully",Toast.LENGTH_SHORT).show();
            img.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
