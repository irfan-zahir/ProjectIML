package com.example.rentaloka;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;

public class UserProfile extends Fragment {
    private View view;

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editFName, editLName;
    private TextView profileFName, profileLName, profileEmail;
    private Button editProfile, saveChanges;
    private ImageView imgProfile;
    private LinearLayout nameLayout, eNameLayout;

    private Uri mImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.user_profile, container, false);

        imgProfile = view.findViewById(R.id.imgProfile);

        editFName = view.findViewById(R.id.editFName);
        editLName = view.findViewById(R.id.editLName);

        editProfile = view.findViewById(R.id.editProfile);
        saveChanges = view.findViewById(R.id.saveChanges);

        profileFName = view.findViewById(R.id.profileFName);
        profileLName = view.findViewById(R.id.profileLName);
        profileEmail = view.findViewById(R.id.profileEmail);

        nameLayout = view.findViewById(R.id.FLNameLayout);
        eNameLayout = view.findViewById(R.id.FLNameEditLayout);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseDatabase.getInstance().getReference("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String uEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        String userID = uEmail.substring(0, uEmail.indexOf("@"));

                        String userEmail = dataSnapshot.child(userID).getValue(User.class).getUseremail();
                        String userFName = dataSnapshot.child(userID).getValue(User.class).getUserfirstname();
                        String userLName = dataSnapshot.child(userID).getValue(User.class).getUserlastname();

                        profileFName.setText(userFName.toUpperCase());
                        profileLName.setText(userLName.toUpperCase());
                        profileEmail.setText(userEmail);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile.setVisibility(View.INVISIBLE);
                nameLayout.setVisibility(View.INVISIBLE);
                editFName.setText(profileFName.getText().toString().trim());
                editLName.setText(profileLName.getText().toString().trim());
                eNameLayout.setVisibility(View.VISIBLE);
                saveChanges.setVisibility(View.VISIBLE);

                imgProfile.setClickable(true);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile.setVisibility(View.VISIBLE);
                nameLayout.setVisibility(View.VISIBLE);
                eNameLayout.setVisibility(View.INVISIBLE);
                saveChanges.setVisibility(View.INVISIBLE);

                String uEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String userID = uEmail.substring(0, uEmail.indexOf("@"));

                FirebaseDatabase.getInstance().getReference("user")
                        .child(userID).child("userfirstname").setValue(editFName.getText().toString().trim());

                FirebaseDatabase.getInstance().getReference("user")
                        .child(userID).child("userlastname").setValue(editLName.getText().toString().trim());

                imgProfile.setClickable(false);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST&&resultCode == RESULT_OK
                &&data != null &&data.getData()!=null){
            mImageUri = data.getData();

        }
    }
}
