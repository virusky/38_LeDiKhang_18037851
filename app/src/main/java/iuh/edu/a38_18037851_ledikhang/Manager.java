package iuh.edu.a38_18037851_ledikhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Manager extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private Button buttonShow;
    private Button buttonAdd;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        auth = FirebaseAuth.getInstance();

        buttonShow = findViewById(R.id.btnShow);
        buttonAdd = findViewById(R.id.btnAdd);
        buttonLogout = findViewById(R.id.btnLogout);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, Show_info.class);
                startActivity(intent);
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, Add_product.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogout.setOnClickListener( v -> {
            auth.signOut();
        });

        stateListener = auth -> {
            if (auth.getCurrentUser() == null) {
                startActivity(new Intent(Manager.this, MainActivity.class));
                finish();
            }
        };
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