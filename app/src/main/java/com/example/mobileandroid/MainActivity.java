package com.example.mobileandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listLaptops;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Laptop> lista = new LinkedList<Laptop>();
    private LaptopAdapter myLaptopAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView textError;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLaptops = (RecyclerView) findViewById(R.id.lista_laptops);
        inicialiceList();
        listLaptops.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listLaptops, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Laptop laptop = (Laptop) lista.get(position);
                Intent i = new Intent(MainActivity.this,LaptopDetail.class);
                i.putExtra("laptop",laptop);
                startActivity(i);
//                Toast.makeText(MainActivity.this, "Presiono una laptop", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void inicialiceList() {
        // Petición GET
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest("http://private-f0eea-mobilegllatam.apiary-mock.com/list",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null) {
                            Log.d("Response String Volley", response.toString());
                            procesarRespuesta(response);
                        }else{
                            Log.d("Response Volley", "Es NULL");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        procesarError();
                        if(error.getMessage() != null) {
                            Log.d("Error Volley", error.getMessage());
                            if( error instanceof NetworkError) {
                                Log.d("Error Volley", "NetworkError");
                            } else if( error instanceof ServerError) {
                                Log.d("Error Volley", "ServerError");
                            } else if( error instanceof AuthFailureError) {
                                Log.d("Error Volley", "AuthFailureError");
                            } else if( error instanceof ParseError) {
                                Log.d("Error Volley", "ParseError");
                            } else if( error instanceof NoConnectionError) {
                                Log.d("Error Volley", "NoConnectionError");
                            } else if( error instanceof TimeoutError) {
                                Log.d("Error Volley", "TimeoutError");
                            }
                        }
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        //VolleySingleton.getInstance(getActivity()).addToRequestQueue(StringRequest);
    }
    private void procesarError() {
//        textError.setText("ocurrió un error. intente más tarde");
//        textError.setVisibility(View.VISIBLE);
    }

    private void procesarRespuesta(JSONArray response) {
        String title,description,image;
        Laptop laptop;
        for (int i=0;i<response.length();i++){
            laptop = new Laptop();
            try {
                JSONObject row = response.getJSONObject(i);
                laptop.setTitle(row.getString("title"));
                laptop.setDescription(row.getString("description"));
                laptop.setImage(row.getString("image"));
                lista.add(laptop);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        myLaptopAdapter = new LaptopAdapter(MainActivity.this,lista);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        listLaptops.setLayoutManager(mLayoutManager);
        listLaptops.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        listLaptops.setAdapter(myLaptopAdapter);
        listLaptops.setItemAnimator(new DefaultItemAnimator());
    }

    private class ClickListener {
    }
}
