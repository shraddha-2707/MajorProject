package com.example.list_of_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<model_list> list;
    private static final String TAG = "MainActivity";
    private String url = "https://shivam7898337488.000webhostapp.com/list_of_item.php";
    private RecyclerView listViewRv;
    itemViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        listViewRv = findViewById(R.id.listViewRv);
        listViewRv.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
        fatchdata();
    }

    private void fatchdata() {
        list = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        Log.d(TAG, "onResponse456: " + obj.getString("list"));

                        JSONArray array1 = new JSONArray(obj.getString("list"));
                        Log.d(TAG, "onResponse23: " + array1.length());

                        model_list model_list = new model_list();
                        for (int j = 0; j < array1.length(); j++) {
                            JSONObject object = array1.getJSONObject(j);
                            Log.d(TAG, "onResponse11: " + object);
                            String productname = object.getString("product_name");
                            String qty = object.getString("qty");
                            String price = object.getString("price");
                            model_list.setVegiee_name(productname);
                            model_list.setQty(qty);

                            Log.d(TAG, "object: " + productname + "  " + qty + "  " + price + " ,  ");
                        }

                        list.add(model_list);
                    }
                    adapter = new itemViewAdapter(MainActivity.this,list);
                    listViewRv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: SSomthindfass");

            }
        });

        requestQueue.add(request);

    }
}
