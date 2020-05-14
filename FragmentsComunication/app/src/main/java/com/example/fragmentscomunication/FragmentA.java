package com.example.fragmentscomunication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {
    public static final String Tag = "frag_a";
    Communicator communicator;
    Button increamentBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_a, container, false);
        init(root);
        handleActions();
        return root;
    }

    private void init(View root) {
        increamentBtn = root.findViewById(R.id.incremetBtn);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (Communicator) getActivity();
    }

    void handleActions(){
        increamentBtn.setOnClickListener((view)->{
            communicator.respond("clicked");
        });
    }
}
