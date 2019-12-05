package com.example.rentaloka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminHistory extends Fragment {

    private View view;

    private String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

    private TextView titleHistory;
    private ImageView imgCalendar;
    private RecyclerView rvHistory;
    private CalendarView calendarView;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<HistoryOrder, adminHistoryHolder> recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_history, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("orderhistory");

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setVisibility(View.INVISIBLE);
        titleHistory = view.findViewById(R.id.titleHistory);
        imgCalendar = view.findViewById(R.id.calender);
        rvHistory = view.findViewById(R.id.recyclerViewHistory);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
                titleHistory.setVisibility(View.INVISIBLE);
                imgCalendar.setVisibility(View.INVISIBLE);
                rvHistory.setVisibility(View.INVISIBLE);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String gYear = Integer.toString(year);
                String gMonth = Integer.toString(month + 1);
                String gDay = Integer.toString(dayOfMonth);

                if(gDay.length()==1){
                    gDay = "0" + gDay;
                }

                String date = gDay + "/" + gMonth + "/" + gYear;
                titleHistory.setText(date);
                loadHistory(date);

                calendarView.setVisibility(View.INVISIBLE);
                titleHistory.setVisibility(View.VISIBLE);
                imgCalendar.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String date = currentDate;
        titleHistory.setText(date);
        loadHistory(date);

    }

    public void loadHistory(String date){
        date = date.replace("/", "");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<HistoryOrder>()
                .setQuery(databaseReference.child(date), HistoryOrder.class)
                .build();
        recyclerAdapter = new FirebaseRecyclerAdapter<HistoryOrder, adminHistoryHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull adminHistoryHolder holder, int position, @NonNull HistoryOrder model) {
                holder.oCustEmail.setText(model.getUserEmail());
                holder.oPlateNum.setText(model.getPlateNumber());
                holder.oUsageDuration.setText(model.getUsageDuration());
                holder.oTotalPayment.setText(model.getTotalPayment());
                holder.odateOrder.setText(model.getOrderDate());
            }

            @NonNull
            @Override
            public adminHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.history_item, parent, false);
                return new adminHistoryHolder(view);
            }
        };

        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.startListening();
        rvHistory.setAdapter(recyclerAdapter);
    }

}
