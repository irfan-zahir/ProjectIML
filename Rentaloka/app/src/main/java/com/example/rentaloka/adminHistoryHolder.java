package com.example.rentaloka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminHistoryHolder extends RecyclerView.ViewHolder {
    TextView oCustEmail, oPlateNum, oUsageDuration, oTotalPayment, odateOrder;

    public adminHistoryHolder(@NonNull View itemView) {
        super(itemView);
        oCustEmail = (TextView) itemView.findViewById(R.id.hCustEmail);
        oPlateNum = (TextView)itemView.findViewById(R.id.hPlateNumber);
        oUsageDuration =(TextView) itemView.findViewById(R.id.hUsageDuration);
        oTotalPayment = (TextView)itemView.findViewById(R.id.hTotalPayment);
        odateOrder = (TextView)itemView.findViewById(R.id.hDateOrder);

    }
}
