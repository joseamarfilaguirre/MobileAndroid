package com.example.mobileandroid.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mobileandroid.domain.data.network.Repo;
import com.example.mobileandroid.model.Laptop;

import java.util.List;

public class LaptopViewModel extends ViewModel {
    private MutableLiveData<List<Laptop>> Laptops = new MutableLiveData<List<Laptop>>();
    private Repo repo = new Repo();

    public MutableLiveData<List<Laptop>> getLaptops(Context context) {
        repo.getLaptops(context).observeForever(new Observer<List<Laptop>>() {
            @Override
            public void onChanged(List<Laptop> laptops) {
                Laptops.setValue(laptops);
            }
        });
        return Laptops;
    }
}
