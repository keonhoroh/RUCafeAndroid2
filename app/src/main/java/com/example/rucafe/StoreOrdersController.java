package com.example.rucafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StoreOrdersController extends AppCompatActivity {


    private Order order;
    private ArrayList<Order> orderList;
    Intent data;

    ListView ordersListView;
    TextView totalOrderPrice;
    Spinner orderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        ordersListView = findViewById(R.id.ordersListView);
        totalOrderPrice = findViewById(R.id.totalOrderPrice);
        orderSpinner = findViewById(R.id.orderSpinner);

        setSpinner();
        setListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelOrder(View view) {
        if (orderSpinner.getSelectedItem().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "There are no orders to remove!", Toast.LENGTH_SHORT).show();
            return;
        }

        int orderNum = Integer.parseInt(orderSpinner.getSelectedItem().toString());
        int indexOfOrder = 0;
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderNum) {
                indexOfOrder = i;
                break;
            }
        }
        orderList.remove(indexOfOrder);
        setSpinner();
        setListView();
        Toast.makeText(getApplicationContext(), "Order Successfully Cancelled", Toast.LENGTH_SHORT).show();
    }
/*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Cancel Order");
        alert.setMessage("Would you like cancel this order?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                orderList.remove(i);
                setSpinner();
                setListView();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }*/

    private void setListView() {
        if (orderSpinner.getSelectedItem().toString().equals("")) {
            String[] list = new String[0];
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            ordersListView.setAdapter(adapter);
            totalOrderPrice.setText("$0.00");
            return;
        }

        int orderNum = Integer.parseInt(orderSpinner.getSelectedItem().toString());
        int indexOfOrder = 0;
        for(int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderNum) {
                indexOfOrder = i;
                break;
            }
        }
        Order selectedOrder = orderList.get(indexOfOrder);

        String[] list = new String[selectedOrder.getItemList().size()];
        for(int i = 0; i < selectedOrder.getItemList().size(); i++) {
            list[i] = selectedOrder.getItemList().get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        //ordersListView.setOnItemClickListener(this);
        ordersListView.setAdapter(adapter);
        totalOrderPrice.setText("$" + String.format("%.2f", selectedOrder.getFinalBill()));
    }

    private void setSpinner() {
        String [] list = new String[orderList.size()];
        for(int i = 0; i < orderList.size(); i++) {
            list[i] = Integer.toString(orderList.get(i).getOrderNum());
        }
        if(orderList.size() == 0) {
            list = new String[1];
            list[0] = "";
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        orderSpinner.setAdapter(adapter);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                setListView();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });



    }

}