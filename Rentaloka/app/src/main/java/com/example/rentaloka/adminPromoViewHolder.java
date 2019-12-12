package com.example.rentaloka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adminPromoViewHolder extends RecyclerView.ViewHolder  {

    TextView promoName, promoDesc, promoID, promoDisc;
    ImageView promoDelete;

    View view;

    public adminPromoViewHolder(@NonNull View itemView) {
        super(itemView);
        promoName = itemView.findViewById(R.id.pLine1);
        promoDesc = itemView.findViewById(R.id.pLine2);
        promoID = itemView.findViewById(R.id.pID);
        promoDelete = itemView.findViewById(R.id.promotionDelete);
        promoDisc = itemView.findViewById(R.id.pDisc);
        view = itemView;
    }
}
