package iuh.edu.a38_18037851_ledikhang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<Product> products;
    private Context context;
    private String url = "https://60b6edb917d1dc0017b8899e.mockapi.io/product";

    public ProductAdapter(Context context) {
        this.context = context;
        update();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);

        holder.count.setText(String.valueOf(position+1));
        holder.type.setText("Type: " + product.getType());
        holder.price.setText("Price: " + product.getPrice() + "$");
        holder.country.setText("Country: " + product.getCountry());

        holder.buttonDelete.setOnClickListener( v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "/" + product.getId(),
                                    response -> {
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                        update();
                                    },
                                    error -> {
                                        error.printStackTrace();
                                    });
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 1, 1));
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(stringRequest);
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        holder.buttonUpdate.setOnClickListener( v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);
            Intent intent = new Intent(context, UpdateItem.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void update() {
        products = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            try {
                for (int i = response.length() - 1; i >= 0; i--) {
                    JSONObject object = (JSONObject) response.get(i);

                    int id = object.getInt("id");
                    String type = object.getString("type");
                    double price = object.getDouble("price");
                    String country = object.getString("country");

                    products.add(new Product(id, type, price, country));
                    Collections.sort(products, new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            Product p1 = (Product) o1;
                            Product p2 = (Product) o2;
                            return new Integer(p1.getId()).compareTo(new Integer(p2.getId()));
                        }
                    });
                }
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(0, 1, 1));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }

}
