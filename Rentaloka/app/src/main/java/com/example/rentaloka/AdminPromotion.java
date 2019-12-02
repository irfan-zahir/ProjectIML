package com.example.rentaloka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPromotion extends Fragment {

    private View adminPromotionView;

    private DatabaseReference databaseReference;
    private Button buttonPromotion;
    private EditText promoName, promoDesc;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Promotion, adminPromoViewHolder> recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adminPromotionView = inflater.inflate(R.layout.admin_promotion, container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference("promotion");

        buttonPromotion = adminPromotionView.findViewById(R.id.buttonPromotion);
        promoName = adminPromotionView.findViewById(R.id.promotionName);
        promoDesc = adminPromotionView.findViewById(R.id.promotionDesc);

        recyclerView = adminPromotionView.findViewById(R.id.recyclerViewPromotion);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return adminPromotionView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Promotion>()
                .setQuery(databaseReference,Promotion.class)
                .build();

        recyclerAdapter = new FirebaseRecyclerAdapter<Promotion, adminPromoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final adminPromoViewHolder holder, int position, @NonNull final Promotion model) {
                holder.promoName.setText(model.getPromotionName());
                holder.promoDesc.setText(model.getPromotionDesc());
                holder.promoID.setText(model.getPromoID());

                holder.promoDelete.setOnClickListener(new View.OnClickListener() {
                    String pID = holder.promoID.getText().toString().trim();
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(pID).removeValue();
                    }
                });
            }

            @NonNull
            @Override
            public adminPromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.promotion_item,parent,false);
                return new adminPromoViewHolder(view);
            }
        };
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.startListening();
        recyclerView.setAdapter(recyclerAdapter);

        buttonPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pName, pDesc, pID;
                pName = promoName.getText().toString().trim();
                pDesc = promoDesc.getText().toString().trim();
                pID = pName.toLowerCase().replace(" ", "");

                databaseReference.child(pID).setValue(new Promotion(pName,pDesc,pID));

                promoName.setText("");
                promoDesc.setText("");
            }
        });

    }

}
