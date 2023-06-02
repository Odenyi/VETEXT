package com.example.vetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class VetDoctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_doctor);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        fauth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigationview);
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.rate).setVisible(false);
        navMenu.findItem(R.id.famerdetails).setVisible(false);
        navMenu.findItem(R.id.Extensiondetails).setVisible(false);
        navMenu.findItem(R.id.Seekadvice).setVisible(false);
        navMenu.findItem(R.id.book).setVisible(false);
        navMenu.findItem(R.id.bookofficer).setVisible(false);


        //to enable clicking
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        // initialize apening of the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //make the fragment to be deafualt
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }


    }

    //if you back press it removes the navigation drawer
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }


    }

    @Override
    //// on select of navigation option
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.home);
                break;
            case R.id.share:
                Toast.makeText(this, "Share", Toast.LENGTH_LONG).show();
                break;


            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Vetdetails:
                Toast.makeText(this, "my details", Toast.LENGTH_LONG).show();

                break;
            case R.id.Schedule:
                Toast.makeText(this, "schedule appiontment", Toast.LENGTH_LONG).show();
                break;
            case R.id.call:
                Toast.makeText(this, "Call Farmer", Toast.LENGTH_LONG).show();
                break;
            case R.id.sendmail:
                Toast.makeText(this, "Sendmail to farmer", Toast.LENGTH_SHORT).show();
            case R.id.logout:
                fauth.signOut();
                Intent logout = new Intent(this, MainActivity.class);
                startActivity(logout);
                break;


        }

        //close drawer on select of item
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}