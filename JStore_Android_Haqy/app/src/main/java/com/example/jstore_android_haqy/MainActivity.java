package com.example.jstore_android_haqy;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;


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
    ArrayList<Supplier> listDataHeader;
    HashMap<Supplier, List<Item>> listDataChild;
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        refreshList();

        listAdapter = new MainListAdapter(MainActivity.this, listSupplier, childMapping);

        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
