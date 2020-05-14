package com.example.fragmentscomunication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class FragmentB extends Fragment {
    public static final String Tag = "frag_b";
    public static final String Counter = "counter";
    TextView incremetText;
    int counter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_b, container, false);
        init(root);
        if (savedInstanceState != null){
            incremetText.setText(String.valueOf(savedInstanceState.getInt(Counter)));
            counter = savedInstanceState.getInt(Counter);
        }
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Counter, counter);
    }

    void init(View view){
        incremetText = view.findViewById(R.id.incremetTxt);
    }
    void handleActions(){
        counter++;
        if (counter!=0)
        incremetText.setText(String.valueOf(counter));

    }

}
