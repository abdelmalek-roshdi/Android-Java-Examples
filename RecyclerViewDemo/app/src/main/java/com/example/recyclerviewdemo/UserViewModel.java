package com.example.recyclerviewdemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserModel> user = new MutableLiveData<>();
    List<UserModel> myCakeList;
    public void setList(List<UserModel> list){
        myCakeList = list;
    }

    public void setCake(UserModel mUser) {
        user.setValue(mUser);
    }
    public LiveData<UserModel> getCake() {
        return user;
    }
}
