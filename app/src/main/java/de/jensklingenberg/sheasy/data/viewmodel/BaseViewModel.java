package de.jensklingenberg.sheasy.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

public class BaseViewModel extends AndroidViewModel {
    public MediatorLiveData<Integer> snack = new MediatorLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}