package com.example.recyclerviewdemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CakeViewModel extends ViewModel {
    private MutableLiveData<Cake> cake = new MutableLiveData<>();
    List<Cake> myCakeList;
    public void setList(List<Cake> list){
        myCakeList = list;
    }

    public void setCake(Cake mCake) {
        cake.setValue(mCake);
    }
    public LiveData<Cake> getCake() {
        return cake;
    }
    
}
