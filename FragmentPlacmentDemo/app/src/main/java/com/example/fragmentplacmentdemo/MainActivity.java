package com.example.fragmentplacmentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button addBtn,removeBtn,replaceBtn;
    Fragment frag1,frag2,frag3,frag4;
    List<Fragment> fragmentList;
    FragmentManager fragmentManager;
    static Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportFragmentManager().beginTransaction().add(R.id.containerLayout,new Fragment1(),Fragment1.Tag).commit();
        init();
        handleActions();
    }

    private void init(){
        addBtn = findViewById(R.id.addBtn);
        removeBtn = findViewById(R.id.removeBtn);
        replaceBtn = findViewById(R.id.replaceBtn);
        fragmentList = new ArrayList<>();
        frag1 = new Fragment1();
        frag2 = new Fragment2();
        frag3 = new Fragment3();
        frag4 = new Fragment4();
        fragmentList.add(frag1);
        fragmentList.add(frag2);
        fragmentList.add(frag3);
        fragmentManager = getSupportFragmentManager();
    }
    private void handleActions(){
        addBtn.setOnClickListener((view)->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                for (Fragment fragment : fragmentList){
                    if (!fragment.isAdded()){
                        fragmentManager.beginTransaction().add(R.id.containerLayout,fragment,fragment.getTag()).addToBackStack(fragment.getTag()).commit();
                        Toast.makeText(MainActivity.this,fragment.getTag(),Toast.LENGTH_SHORT).show();
                        currentFragment = fragment;
                        break;
                    }
                }
            }

        });
        removeBtn.setOnClickListener((view)->{
            fragmentManager.popBackStack();
        });
        replaceBtn.setOnClickListener((view)->{
            if(currentFragment!=null) {
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().add(R.id.containerLayout, frag4, frag4.getTag()).addToBackStack(frag4.getTag()).commit();
            }
        });
    }
}
