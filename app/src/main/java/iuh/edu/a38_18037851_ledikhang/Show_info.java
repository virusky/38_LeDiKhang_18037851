package iuh.edu.a38_18037851_ledikhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Show_info extends AppCompatActivity {

    private String url = "https://60b6edb917d1dc0017b8899e.mockapi.io/product";
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private Button buttonBack;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        recyclerView = findViewById(R.id.recyclerManager);
        buttonBack = findViewById(R.id.btnBackManager);

        adapter = new ProductAdapter(Show_info.this);
        recyclerView.setLayoutManager(new GridLayoutManager(Show_info.this, 1));
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        stateListener = auth -> {
            if (auth.getCurrentUser() == null){
                startActivity( new Intent(Show_info.this, MainActivity.class));
                finish();
            }
        };
        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(Show_info.this,Manager.class));
            finish();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (stateListener != null)
            auth.removeAuthStateListener(stateListener);
    }
}