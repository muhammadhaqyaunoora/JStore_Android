package com.example.jstore_android_haqy;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject item = jsonResponse.getJSONObject(i);
                        int idItem = item.getInt("id");
                        String nameItem = item.getString("name");
                        int priceItem = item.getInt("price");
                        String categoryItem = item.getString("category");
                        String statusItem = item.getString("status");

                        JSONObject supplier = item.getJSONObject("supplier");
                        int idSupplier = supplier.getInt("id");
                        String nameSupplier = supplier.getString("name");
                        String emailSupplier = supplier.getString("email");
                        String phoneNumberSupplier = supplier.getString("phoneNumber");

                        JSONObject location = supplier.getJSONObject("location");
                        String provinceLocation = supplier.getString("province");
                        String descriptionLocation = supplier.getString("description");
                        String cityLocation = supplier.getString("city");

                        Location locationTemp = new Location(provinceLocation, descriptionLocation, cityLocation);
                        Supplier supplierTemp = new Supplier(idSupplier, nameSupplier, emailSupplier, phoneNumberSupplier, locationTemp);
                        Item itemTemp = new Item(idItem, nameItem, priceItem, categoryItem, statusItem, supplierTemp);

                        listSupplier.add(supplierTemp);
                        listItem.add(itemTemp);

                        childMapping.put(listSupplier.get(i), listItem);
                    }
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Refresh failed!");
                }
            }
        };
    }
}
