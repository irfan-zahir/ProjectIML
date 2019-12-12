package com.example.rentaloka;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AdminCar extends Fragment {

    private static final String TAG = "AdminCar";

    private View adminCarView;

    private DatabaseReference databaseReference;

    private RadioGroup adminRadioGroup;
    private Button buttonCar, adminCheckCar;
    private EditText eCarName, ePlateNUm, eCarSeats, eCarPrice;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<CarDetails, adminCarViewHolder> recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adminCarView = inflater.inflate(R.layout.admin_car, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("car");

        adminCheckCar = adminCarView.findViewById(R.id.adminCheckCar);
        adminRadioGroup = adminCarView.findViewById(R.id.adminRadioGroup);

        buttonCar = adminCarView.findViewById(R.id.button_car);
        eCarName = adminCarView.findViewById(R.id.editCarName);
        ePlateNUm = adminCarView.findViewById(R.id.editPNumber);
        eCarSeats = adminCarView.findViewById(R.id.editCarSeats);
        eCarPrice = adminCarView.findViewById(R.id.editPrice);

        recyclerView = adminCarView.findViewById(R.id.recyclerViewCar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return adminCarView;
    }

    @Override
    public void onStart() {
        super.onStart();


        adminCheckCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioID = adminRadioGroup.getCheckedRadioButtonId();

                View radioButton = adminRadioGroup.findViewById(radioID);
                int radioId = adminRadioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) adminRadioGroup.getChildAt(radioId);
                String seats = (String) btn.getText();

                FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<CarDetails>()
                        .setQuery(databaseReference.child(seats), CarDetails.class)
                        .build();

                recyclerAdapter = new FirebaseRecyclerAdapter<CarDetails, adminCarViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final adminCarViewHolder holder, int position, @NonNull final CarDetails model) {
                        holder.rvCarName.setText(model.getCarName());
                        holder.rvPlateNumber.setText(model.getPlateNumber());
                        holder.rvCarSeats.setText(Integer.toString(model.getCarSeats()));
                        holder.rvPrice.setText("RM " + String.format("%.2f", model.getPricepHour()));
                        holder.rvDelete.setOnClickListener(new View.OnClickListener() {
                            String pNumber = holder.rvPlateNumber.getText().toString().trim();
                            String seats = holder.rvCarSeats.getText().toString().trim();

                            @Override
                            public void onClick(View view) {
                                databaseReference.child(seats).child(pNumber.toLowerCase().replace(" ", "")).removeValue();

                                FirebaseDatabase.getInstance().getReference("caradmin")
                                        .child(seats).child(pNumber.toLowerCase().replace(" ", "")).removeValue();
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

        buttonCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carName, plateNum, carStatus;
                int carSeats;
                double carPrice;

                carName = eCarName.getText().toString().trim();
                plateNum = ePlateNUm.getText().toString().trim();
                carSeats = Integer.parseInt(eCarSeats.getText().toString().trim());
                carPrice = Double.parseDouble(eCarPrice.getText().toString().trim());

                carStatus = "free";

                FirebaseDatabase.getInstance().getReference("admincar").child(eCarSeats.getText().toString().trim()).child(plateNum.toLowerCase().replace(" ", ""))
                        .setValue(new CarDetails(plateNum, carName, carSeats, carPrice, carStatus));

                databaseReference.child(eCarSeats.getText().toString().trim()).child(plateNum.toLowerCase().replace(" ", ""))
                        .setValue(new CarDetails(plateNum, carName, carSeats, carPrice, carStatus));

                eCarName.setText("");
                ePlateNUm.setText("");
                eCarSeats.setText("");
                eCarPrice.setText("");
            }
        });
    }
}
