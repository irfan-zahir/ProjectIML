package com.example.rentaloka;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHome extends Fragment {
    private View view;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<OrderDetails, adminOrderViewHolder> adapter;
    private DatabaseReference databaseReference;

    private String email,pNum, duration,tPayment, dateOrder, orderID, newID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_home, container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference("order");

        recyclerView = view.findViewById(R.id.recyclerViewOrder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<OrderDetails>()
                .setQuery(databaseReference,OrderDetails.class)
                .build();


       adapter = new FirebaseRecyclerAdapter<OrderDetails, adminOrderViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull final adminOrderViewHolder holder, int position, @NonNull OrderDetails model) {

               holder.oCustEmail.setText(model.getUserEmail());
               holder.oPlateNum.setText(model.getPlateNumber());
               String uDuration = model.getUsageDuration() + " hours";
               holder.oUsageDuration.setText(uDuration);
               holder.oTotalPayment.setText("RM " + model.getTotalPayment());
               holder.odateOrder.setText(model.getOrderDate());

               holder.carReturned.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       email = holder.oCustEmail.getText().toString().trim();
                       pNum = holder.oPlateNum.getText().toString().trim();
                       duration = holder.oUsageDuration.getText().toString().trim();
                       tPayment = holder.oTotalPayment.getText().toString().trim();
                       dateOrder = holder.odateOrder.getText().toString().trim();
                       newID = dateOrder.replace("/","");
                       orderID = email.substring(0,email.indexOf('@'))+pNum.toLowerCase().replace(" ","");

                       FirebaseDatabase.getInstance().getReference("orderhistory")
                               .addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       FirebaseDatabase.getInstance().getReference("orderhistory").child(newID).child(orderID)
                                               .setValue(new HistoryOrder(email,pNum,duration,tPayment,dateOrder));
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });

                       databaseReference.child(orderID).removeValue();
                   }
               });
           }

           @NonNull
           @Override
           public adminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.order_item,parent,false);
               return new adminOrderViewHolder(view);
           }
       };

        adapter.notifyDataSetChanged();
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
