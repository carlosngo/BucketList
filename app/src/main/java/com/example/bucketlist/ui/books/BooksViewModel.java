package com.example.bucketlist.ui.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BooksViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    BooksFragment booksFragment;

    public BooksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is books fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}