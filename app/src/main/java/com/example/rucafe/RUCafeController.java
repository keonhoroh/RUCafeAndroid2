package com.example.rucafe;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RUCafeController extends AppCompatActivity {

    private static final int STARTING_ORDER_NUM = 1;

    private ArrayList<Order> allOrders;

    private Order currentOrder;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rucafe);
        if(currentOrder == null) {
            currentOrder = new Order(STARTING_ORDER_NUM);
        }
        if (allOrders == null) {
            allOrders = new ArrayList<>();
        }
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    currentOrder = (Order) data.getSerializableExtra("order");
                    allOrders = (ArrayList<Order>) data.getSerializableExtra("allOrders");
                }
            }
        };
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);

    }

    public void openCoffeeView(View view) {
        Intent intent = new Intent(this, CoffeeController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }


    public void openDonutView(View view) {
        Intent intent = new Intent(this, DonutController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }



    public void openOrderingBasketView(View view) {
        if (currentOrder.getItemList().isEmpty()) {
            if (currentOrder.getItemList().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Your shopping cart is empty", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(this, OrderingBasketController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }

    public void openStoreOrdersView(View view) {
        if (allOrders.isEmpty()) {
            Toast.makeText(getApplicationContext(), "There are no orders currently placed", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, StoreOrdersController.class);
        intent.putExtra("currentOrder", currentOrder);
        intent.putExtra("allOrders", allOrders);
        resultLauncher.launch(intent);
    }

    /**
     * Gets the list of all orders that have been made since the program began.
     * @return an ArrayList of type Order that contains every order that has been placed
     */
    public ArrayList<Order> getAllOrders() {
        return allOrders;
    }

    /**
     * Gets the order that is currently being modified by the user.
     * @return the order that is currently in use
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Creates a new Order and replaces the currentOrder with the new one.
     */
    public void createNewOrder() {
        Order newOrder = new Order(currentOrder.getOrderNum() + 1);
        currentOrder = newOrder;
    }

}