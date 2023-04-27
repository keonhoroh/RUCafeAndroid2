package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DonutPurchaseController extends AppCompatActivity {

    private static final String [] amountArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25"};

    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private String type;

    ImageView donutDisplay;
    TextView donutName;
    TextView donutSubTotal;
    Spinner donutAmountSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_purchase);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        donutDisplay = findViewById(R.id.donutDisplay);
        donutName = findViewById(R.id.donutName);
        donutAmountSpinner = findViewById(R.id.donutAmountSpinner);
        donutSubTotal = findViewById(R.id.donutSubTotal);
        donutDisplay.setImageResource(getIntent().getIntExtra("image", 0));
        donutName.setText(getIntent().getStringExtra("flavor"));

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        establishType();
        initializeSpinner();
        updatePrice();
    }

    private void establishType() {
        String flavor = getIntent().getStringExtra("flavor");

        if (flavor.equals("Old Fashioned") || flavor.equals("Chocolate") || flavor.equals("Lemon")) {
            type = "Cake Donuts";
        } else if (flavor.equals("Glazed") || flavor.equals("Powdered") || flavor.equals("Jelly-Filled")) {
            type = "Donut Holes";
        }
        else {
            type = "Yeast Donuts";
        }
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, amountArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        donutAmountSpinner = findViewById(R.id.donutAmountSpinner);
        donutAmountSpinner.setAdapter(adapter);
        donutAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                updatePrice();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
    }

    private void updatePrice() {
        Donut donut = createDonut();
        donutSubTotal.setText(String.format("$%.2f", donut.itemPrice()* donut.getAmount()));
        donutName.setText(donut.toStringDonutView());
    }

    private Donut createDonut() {
        String amountString = donutAmountSpinner.getSelectedItem().toString();
        int amount = Integer.parseInt(amountString);
        return new Donut(type, amount, getIntent().getStringExtra("flavor"), order.getItemNum());
    }

    public void addToOrder(View view) {
        Donut donut = createDonut();
        order.addItem(donut);
        data.putExtra("order", order);
        Toast.makeText(getApplicationContext(), "Donut Added to Order", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}