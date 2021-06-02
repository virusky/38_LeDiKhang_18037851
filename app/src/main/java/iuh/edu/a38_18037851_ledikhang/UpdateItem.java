package iuh.edu.a38_18037851_ledikhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateItem extends AppCompatActivity {
    private String url = "https://60b6edb917d1dc0017b8899e.mockapi.io/product";

    private Product product;

    private Button buttonSave;
    private Button buttonBack;

    private EditText inputType;
    private EditText inputPrice;
    private EditText inputCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        Bundle bundle = getIntent().getExtras();

        inputType = findViewById(R.id.txtTypeUpdate);
        inputPrice = findViewById(R.id.txtPriceUpdate);
        inputCountry = findViewById(R.id.txtCountryUpdate);

        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            inputType.setText(product.getType());
            inputPrice.setText(String.valueOf(product.getPrice()));
            inputCountry.setText(product.getCountry());
        }

        buttonSave = findViewById(R.id.btnSave);
        buttonBack = findViewById(R.id.btnBackUpdate);

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(UpdateItem.this, Show_info.class));
            finish();
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + product.getId(),
                        response -> {
                            startActivity(new Intent(UpdateItem.this, Show_info.class));
                            finish();
                        },
                        error -> {
                            error.printStackTrace();
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", inputType.getText().toString().trim());
                        map.put("price", inputPrice.getText().toString().trim());
                        map.put("country", inputCountry.getText().toString().trim());
                        return map;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 1, 1));
                RequestQueue queue = Volley.newRequestQueue(UpdateItem.this);
                queue.add(stringRequest);
            }
        });
    }
}