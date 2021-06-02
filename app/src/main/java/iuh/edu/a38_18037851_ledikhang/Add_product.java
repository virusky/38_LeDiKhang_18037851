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

public class Add_product extends AppCompatActivity {
    private Product product;
    private Button buttonCreate;
    private Button buttonBack;

    private EditText inputType;
    private EditText inputPrice;
    private EditText inputCountry;
    private String url = "https://60b6edb917d1dc0017b8899e.mockapi.io/product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        inputType = findViewById(R.id.txtTypeAdd);
        inputPrice = findViewById(R.id.txtPriceAdd);
        inputCountry = findViewById(R.id.txtCountryAdd);

        buttonCreate = findViewById(R.id.btnCreate);
        buttonBack = findViewById(R.id.btnBackAdd);

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(Add_product.this, Manager.class));
            finish();
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            startActivity(new Intent(Add_product.this, Show_info.class));
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
                RequestQueue queue = Volley.newRequestQueue(Add_product.this);
                queue.add(stringRequest);
            }
        });
    }
}