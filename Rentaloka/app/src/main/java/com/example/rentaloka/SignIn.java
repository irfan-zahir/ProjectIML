package com.example.rentaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private Button signIn, register;
    private EditText inEmail, inPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    private static final String TAG = "SignIn";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inEmail = (EditText) findViewById(R.id.signin_email);
        inPassword = (EditText) findViewById(R.id.signin_password);
        signIn = (Button) findViewById(R.id.button_signin);
        register = (Button) findViewById(R.id.button_register);

        progressDialog = new ProgressDialog(SignIn.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inEmail.getText().toString().trim();
                final String password = inPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    inEmail.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inPassword.setError("Password is required!");
                }


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!checkAdmin(email,dataSnapshot)){

                            progressDialog.setMessage("Signing in as "+email+"...");
                            progressDialog.show();

                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignIn.this, "Successfully login as  admin!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignIn.this,HomeAdmin.class);
                                        startActivity(intent);

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignIn.this, "Error!: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{

                            progressDialog.setMessage("Signing in as "+email+"...");
                            progressDialog.show();

                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignIn.this, Home.class));

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignIn.this, "Error!: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Register.class));
            }
        });

    }

    public boolean checkAdmin(String email, DataSnapshot dataSnapshot){

        User user = new User();
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            user.setuType(ds.getValue(User.class).getuType());
            user.setUseremail(ds.getValue(User.class).getUseremail());
            if(user.getUseremail().equals(email)&&user.getuType().equals("customer")){
                return true;
            }else if(user.getUseremail().equals(email)&&user.getuType().equals("admin")){
                return false;
            }
        }
        return true;
    }

}
