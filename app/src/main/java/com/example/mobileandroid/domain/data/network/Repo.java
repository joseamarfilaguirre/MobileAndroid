package com.example.mobileandroid.domain.data.network;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileandroid.model.Laptop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Repo {
    public MutableLiveData<List<Laptop>> getLaptops(Context context) {
        MutableLiveData<List<Laptop>> laptops = new MutableLiveData<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest("http://private-f0eea-mobilegllatam.apiary-mock.com/list",
                response -> {
                    if (response != null) {
                        Log.d("Response String Volley", response.toString());
                        procesarRespuesta(response, laptops);
                    } else {
                        Log.d("Response Volley", "Es NULL");
                    }
                },
                error -> {
                    procesarError(laptops);
                    if (error.getMessage() != null) {
                        Log.d("Error Volley", error.getMessage());
                        if (error instanceof NetworkError) {
                            Log.d("Error Volley", "NetworkError");
                        } else if (error instanceof ServerError) {
                            Log.d("Error Volley", "ServerError");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("Error Volley", "AuthFailureError");
                        } else if (error instanceof ParseError) {
                            Log.d("Error Volley", "ParseError");
                        } else if (error instanceof TimeoutError) {
                            Log.d("Error Volley", "TimeoutError");
                        }
                    }
                }
        );
        queue.add(jsonObjectRequest);
        return laptops;
    }

    private void procesarRespuesta(JSONArray response, MutableLiveData<List<Laptop>> laptops) {
        List<Laptop> lista = new LinkedList<>();
        Laptop laptop;
        for (int i = 0; i < response.length(); i++) {
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
        laptops.setValue(lista);
    }
    private void procesarError(MutableLiveData<List<Laptop>> laptops) {
        laptops.setValue(new LinkedList<>());
    }
}

