package com.example.rucafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderingBasketController extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private ArrayAdapter<String> adapter;

    ListView listview;

    TextView subTotal;
    TextView taxAmount;
    TextView totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_basket);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        subTotal = findViewById(R.id.subTotal);
        taxAmount = findViewById(R.id.taxAmount);
        totalPrice = findViewById(R.id.totalPrice);
        listview = findViewById(R.id.listview);
        setListView();
        updatePrices();


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Item");
        alert.setMessage("Would you like to delete this item?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                order.getItemList().remove(i);
                setListView();
                updatePrices();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void placeOrder(View view) {
        if (order.getItemList().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Order Cannot Be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        orderList.add(order);
        order = new Order(order.getOrderNum() + 1);
        data.putExtra("order", order);
        setListView();
        updatePrices();
        Toast.makeText(getApplicationContext(), "Order Successfully Added", Toast.LENGTH_SHORT).show();
    }

    private void setListView() {
        String[] list = new String[order.getItemList().size()];
        for(int i = 0; i < order.getItemList().size(); i++) {
            list[i] = order.getItemList().get(i).toString();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setOnItemClickListener(this);
        listview.setAdapter(adapter);
    }

    private void updatePrices() {
        subTotal.setText("$" + String.format("%.2f", order.getSubTotal()));
        taxAmount.setText("$" + String.format("%.2f", order.getTaxAmount()));
        totalPrice.setText("$" + String.format("%.2f", order.getFinalBill()));

    }

}