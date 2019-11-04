package com.example.bucketlist.ui.films;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FilmsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FilmsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is films fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}