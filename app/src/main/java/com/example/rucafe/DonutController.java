package com.example.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DonutController extends AppCompatActivity {

    private static final String[] flavors = {"Heavy Glazed", "Chocolate Frosted", "Strawberry Sprinkled",
            "Red Velvet", "Fully Chocolate", "Apple Cider", "Old Fashioned", "Chocolate",
            "Lemon", "Glazed", "Powdered", "Jelly-Filled"};
    private static int[] imageIds = {R.drawable.heavyglazed, R.drawable.chocolatefrosted, R.drawable.strawberrysprinkle, R.drawable.redvelvet,
            R.drawable.fullychoco, R.drawable.apple, R.drawable.oldfashioned, R.drawable.chocolatecake, R.drawable.lemoncake,
            R.drawable.glazedhole, R.drawable.powderedhole, R.drawable.jellyhole};

    private Order order;
    private ArrayList<Order> orderList;
    private Intent data;
    private ActivityResultLauncher<Intent> resultLauncher;

    private RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        order = (Order) getIntent().getSerializableExtra("currentOrder");
        orderList = (ArrayList<Order>) getIntent().getSerializableExtra("allOrders");

        data = new Intent();
        data.putExtra("order", order);
        data.putExtra("allOrders", orderList);
        setResult(RESULT_OK, data);

        recycler = findViewById(R.id.recycler);
        setRecycler();

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent newData = result.getData();
                    order = (Order) newData.getSerializableExtra("order");
                    orderList = (ArrayList<Order>) newData.getSerializableExtra("allOrders");
                    setResult(RESULT_OK, newData);
                }
            }
        };
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder {
                ImageView imageView;
                TextView textView;
                public ViewHolder(@NotNull View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.item_image_view);
                    textView = itemView.findViewById(R.id.item_text_view);
                }
            }
            @NotNull
            @Override
            public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_layout, parent, false);
                return new ViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
                int pos = position;
                ((ViewHolder) holder).imageView.setImageResource(imageIds[position]);
                ((ViewHolder) holder).textView.setText(flavors[position]);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchDonutPurchase(flavors[pos], imageIds[pos]);
                    }
                });
            }
            @Override
            public int getItemCount() {
                return flavors.length;
            }
        });
    }

    private void launchDonutPurchase(String flavor, int imageId) {
        Intent intent = new Intent(this, DonutPurchaseController.class);
        intent.putExtra("currentOrder", order);
        intent.putExtra("allOrders", orderList);
        intent.putExtra("flavor", flavor);
        intent.putExtra("image", imageId);

        resultLauncher.launch(intent);
    }

}