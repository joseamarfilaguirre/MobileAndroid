package com.example.mobileandroid.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileandroid.ui.adapter.LaptopAdapter;
import com.example.mobileandroid.ui.viewmodel.LaptopViewModel;
import com.example.mobileandroid.R;
import com.example.mobileandroid.ui.adapter.RecyclerTouchListener;
import com.example.mobileandroid.model.Laptop;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listLaptops;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Laptop> lista = new LinkedList<>();
    private LaptopAdapter myLaptopAdapter;
    private LaptopViewModel laptopViewModel;
    ProgressBar progressBarLaptop;
    private TextView textError;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLaptops = (RecyclerView) findViewById(R.id.lista_laptops);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        listLaptops.setItemAnimator(new DefaultItemAnimator());
        laptopViewModel = ViewModelProviders.of(this).get(LaptopViewModel.class);
        listLaptops.setLayoutManager(mLayoutManager);
        observeData();
        progressBarLaptop = findViewById(R.id.progres_laptops);
        textError = findViewById(R.id.text_error);
        progressBarLaptop.setVisibility(View.VISIBLE);
        progressBarLaptop.setMax(100);
        progressBarLaptop.setIndeterminate(true);
        progressBarLaptop.setProgress(1);
        progressBarLaptop.setVisibility(View.VISIBLE);
        listLaptops.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listLaptops, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Laptop laptop = (Laptop) lista.get(position);
                Intent i = new Intent(MainActivity.this, LaptopDetail.class);
                i.putExtra("laptop", laptop);
                startActivity(i);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void observeData() {
        laptopViewModel.getLaptops(MainActivity.this).observe(this, new Observer<List<Laptop>>() {
            @Override
            public void onChanged(List<Laptop> laptops) {
                if(laptops.size()==0){
                    progressBarLaptop.setProgress(100);
                    progressBarLaptop.setVisibility(View.GONE);
                    textError.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.text_error), Toast.LENGTH_SHORT).show();
                }
                lista=laptops;
                myLaptopAdapter = new LaptopAdapter(MainActivity.this, laptops);
                listLaptops.setAdapter(myLaptopAdapter);
                progressBarLaptop.setProgress(100);
                progressBarLaptop.setVisibility(View.GONE);
                myLaptopAdapter.notifyDataSetChanged();
            }
        });
    }
}
