package com.example.rentaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.user_drawerLayout);
        NavigationView navigationView = findViewById(R.id.user_navView);
        navigationView.setNavigationItemSelectedListener(Home.this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragmentContainer, new UserHome()).commit();
            navigationView.setCheckedItem(R.id.user_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.user_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragmentContainer, new UserHome()).commit();
                break;
            case R.id.user_promotion:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragmentContainer, new UserPromo()).commit();
                break;
            case R.id.user_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_fragmentContainer, new UserProfile()).commit();
                break;
            case R.id.user_logout:
                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(Home.this,SignIn.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
