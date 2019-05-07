package com.example.jstore_android_haqy;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<Supplier> listSupplier = new ArrayList<>();
    private static ArrayList<Item> listItem = new ArrayList<>();
    private static HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    MainListAdapter mainListAdapter;
    ExpandableListView expListView;
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = findViewById(R.id.lvExp);
        tvTest = findViewById(R.id.tvTest);

        refreshList();

        Log.d("finaltest", String.valueOf(MainActivity.listItem.size()));
        Log.d("finaltest", String.valueOf(MainActivity.listSupplier.size()));

        tvTest.setText("Welcome");
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject item = jsonResponse.getJSONObject(i);

                        JSONObject supplier = item.getJSONObject("supplier");

                        JSONObject location = supplier.getJSONObject("location");

                        String province = location.getString("province");
                        String description = location.getString("description");
                        String city = location.getString("city");
                        Location locationTemp = new Location(province, description, city);

                        int supplierId = supplier.getInt("id");
                        String supplierName = supplier.getString("name");
                        String supplierEmail = supplier.getString("email");
                        String supplierNumber = supplier.getString("phoneNumber");

                        Supplier supplierTemp = new Supplier(supplierId, supplierName, supplierEmail, supplierNumber, locationTemp);
                        Log.d("supplier",supplierTemp.getName());


                        if(MainActivity.listSupplier.size()>0){
                            for(Supplier object : MainActivity.listSupplier){
                                if(!(object.getId() == supplierTemp.getId())){
                                    MainActivity.listSupplier.add(supplierTemp);
                                }
                            }
                        }else {
                            MainActivity.listSupplier.add(supplierTemp);
                        }

                        Log.d("supplier size", String.valueOf(MainActivity.listSupplier.size()));

                        int itemId = item.getInt("id");
                        int itemPrice = item.getInt("price");
                        String itemName = item.getString("name");
                        String itemCategory = item.getString("category");
                        String itemStatus = item.getString("status");
                        Item itemTemp = new Item(itemId, itemName, itemPrice, itemCategory, itemStatus, supplierTemp);
                        Log.d("itemTemp",itemTemp.getName());

                        MainActivity.listItem.add(itemTemp);
                    }

                    for(Supplier supplier:MainActivity.listSupplier){
                        ArrayList<Item> tmp = new ArrayList<>();
                        for(Item item:MainActivity.listItem){
                            if(item.getSupplier().getName().equals(supplier.getName())){
                                tmp.add(item);
                            }
                        }
                        MainActivity.childMapping.put(supplier,tmp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("item", String.valueOf(listItem.size()));
                Log.d("test", String.valueOf(MainActivity.listSupplier.size()));

                List<String> listDataHeader= new ArrayList<>();
                HashMap<String, List<String>> listDataChild = new HashMap<>();
                for(Supplier s : listSupplier) {
                    listDataHeader.add(s.getName());
                    ArrayList<Item> tmpItem = childMapping.get(s);
                    ArrayList<String> item = new ArrayList<>();
                    for(Item i : tmpItem){
                        item.add(i.getName());
                    }
                    listDataChild.put(s.getName(),item);
                }

                mainListAdapter = new MainListAdapter(MainActivity.this,listDataHeader,listDataChild);
                expListView.setAdapter(mainListAdapter);
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);

        Log.d("test2", String.valueOf(MainActivity.listSupplier.size()));
    }
}
