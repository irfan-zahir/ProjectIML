package com.example.rentaloka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserPromo extends Fragment {
    private View view;
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Promotion,adminPromoViewHolder> recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.user_promo, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("promotion");

        recyclerView = view.findViewById(R.id.userRVPromotion);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
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
                String disc = Integer.toString(model.getPromoDisc());
                holder.promoDisc.setText(disc+"% off");
                holder.promoDelete.setVisibility(View.INVISIBLE);
                final String promoname = holder.promoName.getText().toString().trim();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getBaseContext(), String.format("Clicked %s", promoname), Toast.LENGTH_SHORT).show();

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
    }
}
