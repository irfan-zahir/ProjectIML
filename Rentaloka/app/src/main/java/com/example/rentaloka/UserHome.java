package com.example.rentaloka;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserHome extends Fragment {
    private View view;
    private DatabaseReference databaseReference;

    public static final String TAG = "Home";

    private String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    private Button checkCar;
    private RadioGroup radioGroup;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<CarDetails, adminCarViewHolder> recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_home, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("car");

        radioGroup = view.findViewById(R.id.RadioGroup);
        checkCar = view.findViewById(R.id.checkCar);

        recyclerView = view.findViewById(R.id.userRVCar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        checkCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*FirebaseDatabase.getInstance().getReference("car").child("4").child("saa3090")
                        .setValue(new CarDetails("SAA 3090", "Proton Wira", 4, 5, "free"));
                FirebaseDatabase.getInstance().getReference("car").child("4").child("nbn1411")
                        .setValue(new CarDetails("NBN 1411", "Proton Iswara", 4, 4.5, "free"));*/

                int radioID = radioGroup.getCheckedRadioButtonId();

                View radioButton = radioGroup.findViewById(radioID);
                int radioId = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                String seats = (String) btn.getText();

                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<CarDetails>()
                        .setQuery(databaseReference.child(seats), CarDetails.class)
                        .build();

                recyclerAdapter = new FirebaseRecyclerAdapter<CarDetails, adminCarViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final adminCarViewHolder holder, int position, @NonNull final CarDetails model) {

                        Log.d(TAG, model.getStatus());
                        holder.rvCarName.setText(model.getCarName());
                        holder.rvPlateNumber.setText(model.getPlateNumber());
                        holder.rvCarSeats.setText(Integer.toString(model.getCarSeats()));
                        String price = Double.toString(model.getPricepHour());
                        holder.rvPrice.setText("RM " + price);
                        holder.rvDelete.setVisibility(View.INVISIBLE);
                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                addOrder(model);// CREATE ORDER

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public adminCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.car_item, parent, false);
                        return new adminCarViewHolder(view);
                    }
                };

                recyclerAdapter.notifyDataSetChanged();
                recyclerAdapter.startListening();
                recyclerView.setAdapter(recyclerAdapter);

            }
        });

    }

    private void addOrder(final CarDetails carDetails) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.user_order, null);

        dialogBuilder.setView(dialogView);

        final String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String userID = userEmail.substring(0, userEmail.indexOf("@"));

        final double carPrice = carDetails.getPricepHour();
        final String plateNumber = carDetails.getPlateNumber();

        final String orderID = userID + plateNumber.toLowerCase().replace(" ", "");

        final EditText promoCode = dialogView.findViewById(R.id.enterPromo);

        final Button checkPayment = dialogView.findViewById(R.id.checkTotal);
        final Button addOrder = dialogView.findViewById(R.id.addOrder);
        final TextView totalPayment = dialogView.findViewById(R.id.tPayment);
        final TextView carName = dialogView.findViewById(R.id.carName);
        final TextView price = dialogView.findViewById(R.id.pricePerHour);
        final EditText usageDuration = dialogView.findViewById(R.id.enterUsageDuration);

        final String promo = promoCode.getText().toString().trim();

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        addOrder.setVisibility(View.INVISIBLE);
        carName.setText(carDetails.getCarName());
        price.setText("RM " + String.format("%.2f", carPrice));

        checkPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!promo.isEmpty()) {
                    FirebaseDatabase.getInstance().getReference("promotion")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    double tPayment = Double.parseDouble(usageDuration.getText().toString().trim()) * carPrice;

                                    double disc = dataSnapshot.child(promoCode.getText().toString().trim())
                                            .getValue(Promotion.class).getPromoDisc() / 100 * tPayment;

                                    tPayment -= disc;

                                    totalPayment.setText("RM " + String.format("%.2f", tPayment));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                } else {

                    double tPayment = Double.parseDouble(usageDuration.getText().toString().trim()) * carPrice;

                    totalPayment.setText("RM " + String.format("%.2f", tPayment));

                }

                addOrder.setVisibility(View.VISIBLE);
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("car").child(Integer.toString(carDetails.getCarSeats()))
                        .child(plateNumber.toLowerCase().replace(" ","")).removeValue();

                FirebaseDatabase.getInstance().getReference("order").child(orderID)
                        .setValue(new OrderDetails(userEmail, plateNumber,
                                usageDuration.getText().toString().trim(),
                                totalPayment.getText().toString().replace("RM ", "").trim(),
                                currentDate, carDetails.getCarSeats()));

                Toast.makeText(getActivity(), "Order Added", Toast.LENGTH_SHORT).show();

                alertDialog.dismiss();
            }
        });
    }

}