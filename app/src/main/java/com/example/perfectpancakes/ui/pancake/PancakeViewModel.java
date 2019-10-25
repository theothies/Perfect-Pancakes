package com.example.perfectpancakes.ui.pancake;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PancakeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PancakeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}