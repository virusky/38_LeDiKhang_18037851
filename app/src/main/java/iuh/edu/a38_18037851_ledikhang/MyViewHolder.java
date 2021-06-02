package iuh.edu.a38_18037851_ledikhang;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView count;
    TextView type;
    TextView price;
    TextView country;

    Button buttonUpdate;
    Button buttonDelete;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        count = itemView.findViewById(R.id.txtCount);
        type = itemView.findViewById(R.id.txtType);
        price = itemView.findViewById(R.id.txtPrice);
        country = itemView.findViewById(R.id.txtCountry);

        buttonDelete = itemView.findViewById(R.id.btnDelete);
        buttonUpdate = itemView.findViewById(R.id.btnUpdate);

    }
}
