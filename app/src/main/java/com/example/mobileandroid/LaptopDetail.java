package com.example.mobileandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class LaptopDetail extends AppCompatActivity {
    private Laptop laptop;
    private TextView title;
    private TextView description;
    private ImageView image;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        laptop = (Laptop) Objects.requireNonNull(getIntent().getExtras()).getParcelable("laptop");
        getSupportActionBar().setTitle("Detalle "+laptop.getTitle().toLowerCase());
        title = findViewById(R.id.title_detail);
        description = findViewById(R.id.description_detail);
        image = findViewById(R.id.image_detail);
        title.setText(laptop.getTitle().toUpperCase());
        description.setText("     "+laptop.getDescription());
        Glide.with(LaptopDetail.this)
                .load(laptop.getImage())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder).centerCrop())
                .into(image);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
