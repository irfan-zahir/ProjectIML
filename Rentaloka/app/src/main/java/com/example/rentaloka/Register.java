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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private EditText editTextFName, editTextLName, editTextEmail, editTextPwd, editTextPwd2;
    private Button send, signIn;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUser = FirebaseDatabase.getInstance().getReference("user");
        mAuth = FirebaseAuth.getInstance();

        editTextFName = (EditText) findViewById(R.id.editTextFName);
        editTextLName = (EditText) findViewById(R.id.editTextLName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPwd = (EditText) findViewById(R.id.editTextPwd);
        editTextPwd2 = (EditText) findViewById(R.id.editTextPwd2);
        send = (Button) findViewById(R.id.submit);
        signIn = (Button) findViewById(R.id.signIn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, SignIn.class));
            }
        });

    }

    private void addUser() {
        final String firstname = editTextFName.getText().toString().trim();
        final String lastname = editTextLName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPwd.getText().toString().trim();
        String password2 = editTextPwd2.getText().toString().trim();
        final String uType = "customer";

        progressDialog = new ProgressDialog(Register.this);

        progressDialog.setMessage("Registering "+ email);
        progressDialog.show();

        if (!TextUtils.isEmpty(firstname)){
            if (!TextUtils.isEmpty(lastname)){
                if (!TextUtils.isEmpty(email)){
                    if (password.length()>6){
                        if (password.equals(password2)){

                            databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(checkEmail(email,dataSnapshot)){
                                        progressDialog.setMessage("Email have been used..Redirecting to Sign In page.");
                                        progressDialog.show();
                                        Toast.makeText(Register.this, "Email have been used", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Register.this, SignIn.class));
                                    }else{
                                        String id = email.toLowerCase().substring(0,email.indexOf('@'));
                                        User user = new User(id, firstname, lastname, email, password, uType);
                                        databaseUser.child(id).setValue(user);
                                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, "User successfully added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Register.this,SignIn.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }else{
                        editTextPwd.setText("");
                        editTextPwd2.setText("");
                        Toast.makeText(this, "Password is less than 6 words", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else {
                    Toast.makeText(this, "You should enter your email", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }else{
                Toast.makeText(this, "You should enter your last name", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }else{
            Toast.makeText(this, "You should enter your first name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public boolean checkEmail(String email, DataSnapshot dataSnapshot){
        User user = new User();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setUseremail(ds.getValue(User.class).getUseremail());
            if(user.getUseremail().equals(email)){
                return  true;
            }
        }
        return false;
    }
}