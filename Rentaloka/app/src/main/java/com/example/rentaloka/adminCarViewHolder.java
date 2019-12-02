package com.example.rentaloka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminCarViewHolder extends RecyclerView.ViewHolder {

    TextView rvCarName, rvPlateNumber, rvCarSeats,rvPrice;
    ImageView rvDelete;

    public adminCarViewHolder(@NonNull View itemView) {
        super(itemView);
        rvCarName = itemView.findViewById(R.id.rvCarName);
        rvPlateNumber = itemView.findViewById(R.id.rvPlateNumber);
        rvCarSeats = itemView.findViewById(R.id.rvCarSeats);
        rvPrice = itemView.findViewById(R.id.rvPrice);
        rvDelete = itemView.findViewById(R.id.rvDelete);

    }
}
