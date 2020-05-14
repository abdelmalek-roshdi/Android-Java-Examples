package com.example.viewmodelfragmentcommunication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TextViewModel extends ViewModel {
    private MutableLiveData<Integer> text = new MutableLiveData<>();
    int count;

    public void setText() {
        text.setValue(++count);
    }
    public LiveData<Integer> getText() {
        return text;
    }
}
