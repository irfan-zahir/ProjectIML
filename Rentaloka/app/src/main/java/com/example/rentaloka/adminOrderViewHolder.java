package com.example.rentaloka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminOrderViewHolder extends RecyclerView.ViewHolder {
    TextView oCustEmail, oPlateNum, oUsageDuration, oTotalPayment, odateOrder;
    ImageView carReturned;

    public adminOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        oCustEmail = (TextView) itemView.findViewById(R.id.custEmail);
        oPlateNum = (TextView)itemView.findViewById(R.id.plateNumber);
        oUsageDuration =(TextView) itemView.findViewById(R.id.usageDuration);
        oTotalPayment = (TextView)itemView.findViewById(R.id.totalPayment);
        odateOrder = (TextView)itemView.findViewById(R.id.dateOrder);
        carReturned = (ImageView) itemView.findViewById(R.id.carReturned);

    }
}
